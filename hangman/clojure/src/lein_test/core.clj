(ns lein-test.core
	(:gen-class)
	(:require [compojure.core :as comp]
						[clojure.string :as str]
						[ring.util.response :as ring-resp]
						[ring.middleware.json :refer [wrap-json-response]]
						[ring.middleware.defaults :as ring-def]
						[compojure.route :refer [resources]]
						[ring.util.response :refer [redirect, redirect-after-post]]
						[lein-test.words :as words]))

(comp/defroutes my-routes
 (comp/context "/services" []
	 (comp/GET "/word/random" [] 
		(ring-resp/redirect (str "/services/word/" (:id (words/get-random-word)))))
	(wrap-json-response
	 (comp/GET "/word/:id{\\d+}" [id]
		(ring-resp/response
		 (let [word (words/get-word-by-id (Integer/parseInt id))]
			{:id id :length (count word)}))))
	(wrap-json-response
	 (comp/GET "/word/:id/get_letter_ocurrences" [letter id]
		(ring-resp/response
		 (words/get-letter-ocurrences
			(first (str/lower-case letter))
			(words/get-word-by-id (Integer/parseInt id))))))
	(wrap-json-response
	 (comp/GET "/word/:id{\\d+}/guess" [id guess]
		(ring-resp/response {:correct (words/guess-word (Integer/parseInt id) guess)})))))

(def app
 (ring-def/wrap-defaults my-routes ring-def/site-defaults))
