(ns todoapp.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub :todos
  #(vals (:sorted-todos %)))

(reg-sub :showing
  #(:showing %))

(reg-sub :loading
  #(:loading %))

(reg-sub :done-count
  :<- [:todos]
  (fn [todos]
    (count (filter :done todos))))

(reg-sub :visible-todos
  :<- [:todos]
  :<- [:showing]
  (fn [[todos showing] _]
    (filter (case showing
              :done :done
              :active (complement :done)
              :all identity) todos)))
