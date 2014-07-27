(ns dnd-memo-scheduler.routes.home
  (:require [compojure.core :refer :all]
            [dnd-memo-scheduler.layout :as layout]
            [dnd-memo-scheduler.util :as util]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
