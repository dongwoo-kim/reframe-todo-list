(ns todoapp.events
  (:require [re-frame.core :refer [reg-event-db]]))

(def todo-id (atom 2))

(reg-event-db
  :add-todo
  (fn [db [_ value]]
    (swap! todo-id inc)
    (assoc-in db [:sorted-todos @todo-id] {:id @todo-id
                                           :title value
                                           :done false})))

(reg-event-db
  :set-showing
  (fn [db [_ value]]
    (assoc-in db [:showing] value)))

(reg-event-db
  :delete-todo
  (fn [db [_ id]]
    (update-in db [:sorted-todos] dissoc id)))

(reg-event-db
  :done-todo
  (fn [db [_ id]]
    (update-in db [:sorted-todos id :done] not)))
