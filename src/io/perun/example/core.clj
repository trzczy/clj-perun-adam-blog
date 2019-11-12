(ns io.perun.example.core
  (require [clojure.string :as str])
  (require [clj-time.format :as f])
  (require [clj-time.coerce :as c])
  ;;  (require [io.perun.example.menufunctions :as menuf])
  (require [io.perun.example.menu.hiccup :as hiccup])
  (require [io.perun.example.menu.style :as style])
  (require [io.perun.example.menu.order :as order])
  (require [io.perun.example.menu.regen :as regen])
  (use [hiccup.page :refer (include-css include-js)]))
(defn date-formatted [date-published]
  (f/unparse (f/formatter "dd MMM yyyy") (c/from-date date-published)))
(defn head
  [meta & [page type]]
  [:head
   [:meta {:charset "utf-8"}]
   [:meta
    {:name "viewport",
     :content
     "width=device-width, initial-scale=1.0, user-scalable=no"}]
   [:title (str (:site-title meta) " | " (if page page "page"))]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
   (include-css
     "/assets/css/foundation.css"
     "/assets/css/app.css"
     "/assets/css/print.css"
     "/assets/css/pygments-css-master/borland.css"
   )
   (if
     (= type :post)
;;     [:link {:rel "stylesheet" :media "all" :href "/assets/css/tty.css"}]
     [:link {:rel "stylesheet" :media "tty" :href "/assets/css/tty.css"}]
   )
   [:meta {:class "foundation-mq"}]])

(defn header [meta feat]
  [:header#header
   [:div.title-bar
    {:data-responsive-toggle "example-menu", :data-hide-for "medium"}
    [:button.menu-icon {:type "button", :data-toggle "example-menu"}]
    [:div.title-bar-title "Menu"]]
   [:div#example-menu.top-bar
    [:div.top-bar-left
     [:nav#menu
      ((comp
        hiccup/gen
        (partial style/menu feat)
        regen/regen
        (partial order/order-to-list (:menu-base meta)))
       (:add-all-menu-data meta))]]
    [:div.top-bar-right
     [:ul.menu
      [:li [:input {:type "search", :placeholder "Search"}]]
      [:li [:button.button {:type "button"} "Search"]]]]]
   [:div.row
    [:div.columns
     [:p.motto.decoration "For those about to code"]]]
   [:div.row
    [:div.columns
     [:button.button.float-right {:onclick "window.print();"} "Print this Page"]]]
   ])

(defn scripts []
  (list
   [:script {:src "js/vendor/jquery.js"}]
   [:script {:src "js/vendor/what-input.js"}]
   [:script {:src "js/vendor/foundation.js"}]
   [:script {:src "js/app.js"}]))


   ;;[:title (str (:site-title meta) " | " (:tag entry))]
    ;;"/assets/css/screen.css"
    ;;"//fonts.googleapis.com/css?family=Noto+Serif:400,700,400italic%7COpen+Sans:700,400"
    ;;"/assets/css/prism.css"
    ;;"/assets/css/my.css"
