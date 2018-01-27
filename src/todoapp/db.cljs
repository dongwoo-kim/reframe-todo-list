(ns todoapp.db
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :initialize
  (fn []
    {:showing :all
     :loading false
     :sorted-todos (sorted-map)}))
