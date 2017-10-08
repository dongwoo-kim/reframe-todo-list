(ns todoapp.core
  (:require [reagent.core :refer [render-component]]
            [re-frame.core :refer [dispatch-sync]]
            [todoapp.db]
            [todoapp.events]
            [todoapp.subs]
            [todoapp.views :refer [container]]))

(defn on-js-reload []
  (js/console.log "reload!!")
  (dispatch-sync [:initialize])
  (render-component [container] (js/document.getElementById "app")))
