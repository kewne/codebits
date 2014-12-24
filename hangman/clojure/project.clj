(defproject hangman "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"],
		 [org.clojure/java.jdbc "0.3.6"],
		 [ring/ring-json "0.3.1"],
		 [ring/ring-defaults "0.1.2"],
		 [org.xerial/sqlite-jdbc "3.8.7"],
		 [ring/ring-core "1.3.1"],
		 [ring/ring-jetty-adapter "1.3.1"],
		 [org.clojure/tools.namespace "0.2.7"],
		 [compojure "1.3.1"]]
  :profiles {:uberjar {:aot :all}}
	:plugins [[lein-ring "0.8.13"],
						[lein-less "1.7.2"],
						[lein-tar "1.1.2"]]
  :ring {:handler lein-test.core/app})
