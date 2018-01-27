(ns todoapp.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [ajax.core :as ajax]))

(reg-event-fx
  :fetch-data
  (fn [{:keys [db]} _]
    {:db (assoc db :loading true)
     :http-xhrio {:method :get
                  :uri "/api/todos"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:fetch-data-success]}}))

(reg-event-fx
  :add-todo
  (fn [{:keys [db]} [_ title]]
    {:db (assoc db :loading true)
     :http-xhrio {:method :post
                  :params {:title title}
                  :uri "/api/todos"
                  :format (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:add-todo-success]}}))

(reg-event-fx
  :delete-todo
  (fn [{:keys [db]} [_ id]]
    {:db (assoc db :loading true)
     :http-xhrio {:method :delete
                  :uri (str "/api/todos/" id)
                  :format (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:delete-todo-success]}}))

(reg-event-fx
  :toggle-todo
  (fn [{:keys [db]} [_ id]]
    {:db (assoc db :loading true)
     :http-xhrio {:method :put
                  :uri (str "/api/todos/" id "/toggle")
                  :format (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [:toggle-todo-success]}}))

(reg-event-db
  :fetch-data-success
  (fn [db [_ res]]
    (assoc db :loading false
              :sorted-todos (into (sorted-map) (map #(vec [(:id %) %]) res)))))

(reg-event-db
  :add-todo-success
  (fn [db [_ res]]
    (let [{:keys [id title]} res]
      (-> db
        (assoc :loading false)
        (assoc-in [:sorted-todos id] {:id id
                                      :title title
                                      :done false})))))

(reg-event-db
  :delete-todo-success
  (fn [db [_ res]]
    (-> db
      (assoc :loading false)
      (update-in [:sorted-todos] dissoc (:id res)))))

(reg-event-db
  :toggle-todo-success
  (fn [db [_ res]]
    (let [{:keys [id done]} res]
      (-> db
        (assoc :loading false)
        (assoc-in [:sorted-todos id :done] done)))))

(reg-event-db
  :set-showing
  (fn [db [_ value]]
    (assoc db :showing value)))
