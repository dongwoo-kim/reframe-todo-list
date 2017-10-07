(ns todoapp.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub :title
  (fn [db _]
    (:title db)))

(reg-sub :todos
  (fn [db _]
    (:todos db)))
