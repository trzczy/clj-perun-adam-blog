(ns io.perun.example.views.post
  (:require
   [hiccup.page :refer [html5]]
   [beautify-web.core :as bw]
   [io.perun.example.core :as core]))

(defn render [{meta :meta posts :entries post :entry}]
  (bw/beautify-html
  (html5 {:lang "en" :itemtype "http://schema.org/Blog"}
         (core/head meta (:title post) :post)
         [:body

;;[:pre post]
;;[:pre (:permalink post)]

          (core/header meta {:site-title (:title post) :h1-menu-item :out-of-menu :active :out-of-menu :logo :site})
          [:main
           {:role "main"}
             [:article
              [:div.row
               [:div.columns
                [:time (core/date-formatted (:date-published post))]]]
              [:div.row
               [:div.columns
                [:a {:href (:permalink post)} [:h1 (:title post)]]]]
              [:div.row
               [:div.columns
                [:div (:content post)]]]
              [:div.row
               [:div.columns
                (for [tag (:tags post)]
                  (list [:a {
                             :style "color:#654; text-decoration: none;"
                             :href (str tag ".html")}
                         (str "#" tag)]
                        " "))]]]]
          (core/scripts)])))
