(ns todoapp.views
  (:require [reagent.core :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]
            [todoapp.routes :refer [url-for]]))

(defn todo-input []
  (let [value (atom "")
        on-change #(reset! value (-> % .-target .-value))
        on-submit #(do (.preventDefault %)
                       (dispatch [:add-todo @value])
                       (reset! value ""))]
    (fn []
      [:form {:on-submit on-submit}
       [:input {:on-change on-change
                :value @value}]
       " "
       [:button "ADD"]])))

(defn todo-item [todo]
  (let [id (:id todo)]
    [:li {:class (if (:done todo) "done" "")}
     [:input {:type :checkbox
              :checked (:done todo)
              :on-change #(dispatch [:done-todo id])}]
     [:span (str " " (:title todo) " ")]
     [:input {:type :button
              :value "X"
              :on-click #(dispatch [:delete-todo id])}]]))

(defn state-item [key name count]
  (let [showing @(subscribe [:showing])
        class-name (if (= showing key) "current-state" "")]
    [:li
     [:a {:href (url-for key)
          :class class-name}
      (str name " (" count ")")]]))

(defn todo-state []
  (let [todos @(subscribe [:todos])
        done-count @(subscribe [:done-count])
        all-count (count todos)
        active-count (- all-count done-count)]
    [:ul
     [state-item :all "All" all-count]
     [state-item :active "Active" active-count]
     [state-item :done "Done" done-count]]))

(defn container []
  (let [visible-todos @(subscribe [:visible-todos])
        showing @(subscribe [:showing])]
    [:div
     [:h1 "TODO LIST"]
     [todo-input]
     [:ul
      (for [todo visible-todos]
        ^{:key (:id todo)}[todo-item todo])]
     [todo-state]]))
