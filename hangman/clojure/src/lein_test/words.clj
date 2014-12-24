(ns lein-test.words
	(:require [clojure.string :as str]
						[clojure.java.jdbc :as jdbc]))

(def db { :subprotocol "sqlite"
				 :subname "hangman.db" })

(defn get-word-count []
	(:count (first (jdbc/query db ["select count(*) as count from WORDS"]))))

(defn get-random-word []
 (let [n (inc (rand-int (get-word-count)))]
	(let [{id :w_id word :word} (first (jdbc/query db ["select * from WORDS limit ?,1" n]))]
	 {:id n :word word})))

(defn get-word-by-id [id] (:word (first (jdbc/query db ["select * from WORDS where W_ID = ?" id]))))

(defn guess-word [id guess] (= (str/lower-case guess) (get-word-by-id id)))

(defn get-letter-ocurrences [letter word]
	 (keep-indexed #(when (= %2 letter) %1) word))
