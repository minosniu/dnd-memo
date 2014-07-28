(ns dnd-memo.handler
  (:require [compojure.core :refer [defroutes]]
            [dnd-memo.routes.home :refer [home-routes]]
            [dnd-memo.middleware :refer [load-middleware]]
            [noir.response :refer [redirect]]
            [noir.util.middleware :refer [app-handler]]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.rotor :as rotor]
            [selmer.parser :as parser]
            [environ.core :refer [env]]
            [dnd-memo.routes.auth :refer [auth-routes]]))

(defroutes
  app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (timbre/set-config!
    [:appenders :rotor]
    {:min-level :info,
     :enabled? true,
     :async? false,
     :max-message-per-msecs nil,
     :fn rotor/appender-fn})
  (timbre/set-config!
    [:shared-appender-config :rotor]
    {:path "dnd_memo.log", :max-size (* 512 1024), :backlog 10})
  (if (env :dev) (parser/cache-off!))
  (timbre/info "dnd-memo started successfully"))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (timbre/info "dnd-memo is shutting down..."))

(def app
 (app-handler
   [auth-routes home-routes app-routes]
   :middleware
   (load-middleware)
   :session-options
   {:timeout (* 60 30), :timeout-response (redirect "/")}
   :access-rules
   []
   :formats
   [:json-kw :edn]))

