(ns io.perun.example.menu.hiccup)

(defn add-to-key [key add]
  (keyword (str (name key) add)))

(defn gen
  [data] [:ul.dropdown.menu
          {:data-dropdown-menu "data-dropdown-menu" :role "menubar"}
          (for [item data]
            (let [
                  properties
                  {:href (:href item) :title (:title item)}
                  atag
                  ((comp
                    #(if (true? (:active item))
                      (add-to-key % ".active")
                      %)
                    #(if (true? (:logo item))
                      (add-to-key % ".logo")
                      %)):a)]
              [:li {:role "menuitem"}
               (if (true? (:h1 item))
                 [:h1.menu [atag properties (:name item)]]
                 [atag properties (:name item)])]))])
