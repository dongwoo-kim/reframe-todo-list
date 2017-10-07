(ns todoapp.db
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :initialize
  (fn [_ _]
    {:title "Todo List"
     :todos (sorted-map 1 {:id 1
                           :title "Todo 1"
                           :done false}
                        2 {:id 2
                           :title "Todo 2"
                           :done false})}))
