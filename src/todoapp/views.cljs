(ns todoapp.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(def <sub (comp deref rf/subscribe))
(def >evt rf/dispatch)

(defn input-form []
  (let [value (r/atom "")
        on-change #(reset! value (-> % .-target .-value))
        on-submit (fn [e]
                    (.preventDefault e)
                    (>evt [:add-todo @value])
                    (reset! value ""))]
    (fn []
      [:form {:on-submit on-submit}
       [:input {:on-change on-change
                :value @value}]
       [:button "ADD"]])))

(defn todo-item [todo]
  (let [id (:id todo)]
    [:li {:style {:text-decoration (if (:done todo)
                                     "line-through"
                                     "none")}}
     [:input {:type :checkbox
              :checked (:done todo)
              :on-change #(>evt [:done-todo id])}]
     [:span (str " " (:title todo) " ")]
     [:input {:type :button
              :value "X"
              :on-click #(>evt [:delete-todo id])}]]))

(defn container []
  (let [title (<sub [:title])
        todos (<sub [:todos])]
    [:div
     [:h1 title]
     [input-form]
     [:ul
      (for [[_ todo] todos]
        ^{:key (:id todo)} [todo-item todo])]]))
