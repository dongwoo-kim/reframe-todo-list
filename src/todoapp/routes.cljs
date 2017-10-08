(ns todoapp.routes
  (:require [bidi.bidi :as bidi]
            [re-frame.core :refer [dispatch]]
            [pushy.core :refer [pushy]]))

(def routes
  ["/"
   [["" :all]
    ["active" :active]
    ["done" :done]]])

(defn set-page! [match]
  (dispatch [:set-showing (:handler match)]))

(def history
  (pushy set-page! (partial bidi/match-route routes)))

(def url-for (partial bidi/path-for routes))
