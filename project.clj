(defproject todoapp "0.1.0-SNAPSHOT"
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.908"]
                 [org.clojure/core.async  "0.3.443"]
                 [reagent "0.8.0-alpha2"]
                 [re-frame "0.10.3-rc2"]
                 [bidi "2.1.2"]
                 [kibu/pushy "0.3.8"]]

  :plugins [[lein-figwheel "0.5.13"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {:on-jsload "todoapp.core/main"
                           :open-urls ["http://localhost:3449/index.html"]}
                :compiler {:main todoapp.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/todoapp.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :closure-defines {"re_frame.trace.trace_enabled_QMARK_" true}
                           :preloads [devtools.preload day8.re-frame.trace.preload]}}
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/todoapp.min.js"
                           :main todoapp.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {:http-server-root "public"
             :server-port 3449
             :server-ip "127.0.0.1"
             :css-dirs ["resources/public/css"]
             :nrepl-port 7888}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [figwheel-sidecar "0.5.13"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [day8.re-frame/trace "0.1.14"]
                                  [proto-repl "0.3.1"]]
                   :source-paths ["src" "dev"]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
