(ns lein-test.words-test
	(:require [lein-test.words :refer :all]
						[clojure.test :refer :all]))

(deftest test-get-letter-ocurrences
	(is (= (seq '(0)) (get-letter-ocurrences \a "a")))
	(is (empty? (get-letter-ocurrences \a "")))
	(is (= (seq '(0 2)) (get-letter-ocurrences \a "agap2"))))
