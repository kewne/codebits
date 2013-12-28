(ns fizz-buzz.core)

(defn mod-eq-zero
    [x y]
    (= 0 (mod x y)))

(defn fizz-buzz
    [x]
    (cond
        (and (mod-eq-zero x 3) (mod-eq-zero x 5)) "fizzbuzz"
        (mod-eq-zero x 3) "fizz"
        (mod-eq-zero x 5) "buzz"
        true (Integer/toString x)))

(defn -main
    []
    (doseq
    [n (range 1 50)]
    (println (fizz-buzz n))))
