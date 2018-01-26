(ns server.handler
  (:require [compojure.core :refer [GET POST DELETE PUT context defroutes]]
            [compojure.route :refer [not-found]]
            [ring.middleware.json :as middleware]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.util.response :refer [response]]))

(def last-id (atom 0))
(def todos (atom (sorted-map)))

(defn add-todo [title]
  (let [id (swap! last-id inc)]
    (swap! todos assoc id {:id id
                           :title title
                           :done false})))

(defn delete-todo [id]
  (swap! todos dissoc id))

(defn toggle-todo [id]
  (swap! todos update-in [id :done] not))

(defroutes routes
  (context "/api/todos" []
    (GET "/" [] (response (vec (vals @todos))))
    (POST "/" [title]
      (add-todo title)
      (response {:success true :id @last-id}))
    (PUT "/:id/toggle" [id]
      (toggle-todo (Long. id))
      (response {:success true :done (get-in @todos [(Long. id) :done])}))
    (DELETE "/:id" [id]
      (delete-todo (Long. id))
      (response {:success true})))
  (not-found "Not Found"))

(def app
  (-> routes
      wrap-keyword-params
      middleware/wrap-json-params
      middleware/wrap-json-response))
