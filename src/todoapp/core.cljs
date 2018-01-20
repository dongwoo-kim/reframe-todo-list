(ns todoapp.core
  (:require [reagent.core :refer [render-component]]
            [re-frame.core :refer [dispatch-sync]]
            [pushy.core :as pushy]
            [todoapp.db]
            [todoapp.events]
            [todoapp.subs]
            [todoapp.views :refer [container]]
            [todoapp.routes :refer [history]]))

(defonce _init (dispatch-sync [:initialize]))

(do
  (pushy/start! history)
  (dispatch-sync [:initialize])
  (render-component [container] (js/document.getElementById "app")))
