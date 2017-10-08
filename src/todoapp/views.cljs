(ns todoapp.views
  (:require [reagent.core :refer [atom]]
            [re-frame.core :refer [subscribe dispatch]]))

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
       [:button "ADD"]])))

(defn todo-item [todo]
  (let [id (:id todo)]
    [:li {:style {:text-decoration (if (:done todo)
                                     "line-through"
                                     "none")}}
     [:input {:type :checkbox
              :checked (:done todo)
              :on-change #(dispatch [:done-todo id])}]
     [:span (str " " (:title todo) " ")]
     [:input {:type :button
              :value "X"
              :on-click #(dispatch [:delete-todo id])}]]))

(defn state-item [key name count]
  (let [showing @(subscribe [:showing])
        class-name (if (= showing key) "current-state" "")
        on-click #(do (.preventDefault %)
                      (dispatch [:set-showing key]))]
    [:li
     [:a {:href (str "#" name)
          :on-click on-click
          :class class-name}
      (str name " (" count ")")]]))

(defn todo-state [todos]
  (let [all-count (count todos)
        done-count @(subscribe [:done-count])
        active-count (- all-count done-count)]
    [:ul
     [state-item :all "All" all-count]
     [state-item :active "Active" active-count]
     [state-item :done "Done" done-count]]))

(defn container []
  (let [todos @(subscribe [:visible-todos])
        showing @(subscribe [:showing])]
    [:div
     [:h1 "TODO List"]
     [todo-input]
     [:ul
      (for [todo todos]
        ^{:key (:id todo)}[todo-item todo])]
     [todo-state todos]]))
