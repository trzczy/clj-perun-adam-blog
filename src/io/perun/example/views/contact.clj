(ns io.perun.example.views.contact
  (:require
   [hiccup.page :refer [html5]]
   [beautify-web.core :as bw]
   [io.perun.example.core :as core]))

(defn render [{meta :meta posts :entries post :entry}]
  (bw/beautify-html
   (html5
    {:lang "en" :itemtype "http://schema.org/Blog"}
    (core/head meta (:title post))
    [:body
     (core/header meta {:active :contact :logo :site})



     [:main
      {:role "main"}
      [:article
       [:div.row
        [:div.columns
         [:a {:href (:permalink post)}
          [:h1 (:title post)]]]]
       [:div.row
        [:div.columns
         [:div (:content post)]]]]]
     (core/scripts)])))



