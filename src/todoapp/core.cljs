(ns todoapp.core
  (:require [reagent.core :refer [render-component]]
            [re-frame.core :refer [dispatch-sync]]
            [day8.re-frame.http-fx]
            [pushy.core :as pushy]
            [todoapp.db]
            [todoapp.events]
            [todoapp.subs]
            [todoapp.views :refer [container]]
            [todoapp.routes :refer [history]]))

(defonce _init (do (dispatch-sync [:initialize])
                   (dispatch-sync [:fetch-data])))

(do
  (pushy/start! history)
  ; (dispatch-sync [:initialize])
  ; (dispatch-sync [:fetch-data])
  (render-component [container](js/document.getElementById "app")))
