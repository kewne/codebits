main = putStrLn . unlines . map num2fizzBuzz $ [1..15]

num2fizzBuzz :: Int -> String
num2fizzBuzz x
    | isFizz x && isBuzz x = "fizzbuzz"
    | isFizz x = "fizz"
    | isBuzz x = "buzz"
    | otherwise  = show x
    where isFizz x = mod x 3 == 0
          isBuzz x = mod x 5 == 0
