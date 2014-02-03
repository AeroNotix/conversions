(ns conversions.uuid-test
  (:require [com.aerokode.conversions.uuid :refer :all]
            [clojure.test :refer :all]))

(deftest to-and-fro
  (let [uuid (java.util.UUID/randomUUID)]
    (is (= (uuid-from-base64 (uuid-to-base64 uuid))))))
