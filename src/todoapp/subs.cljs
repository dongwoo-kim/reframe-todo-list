(ns todoapp.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub :todos
  (fn [db]
    (vals (:sorted-todos db))))

(reg-sub :showing
  (fn [db]
    (:showing db)))

(reg-sub :done-count
  :<- [:todos]
  (fn [todos]
    (count (filter :done todos))))

(reg-sub :visible-todos
  :<- [:todos]
  :<- [:showing]
  (fn [[todos showing] _]
    (let [filter-fn (case showing
                      :active (complement :done)
                      :done :done
                      :all identity)]
      (filter filter-fn todos))))
