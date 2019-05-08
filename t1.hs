isVowel :: Char -> Bool 
isVowel x = if x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' then True 
		

addComma :: [String] -> [String]
addComma l = map (++",") l

htmlListItemsLombada :: [String] -> [String]
htmlListItemsLombada l = map (\xl -> "<LI>" ++ xl ++ "</LI>") l

isntVowel :: Char -> Bool
isntVowel x = not(isVowel x)

semVogais :: String -> String
semVogais string = filter isntVowel string

isLetter :: String -> Bool
isLetter str = if str  == ' ' then True

confere :: String -> String
confere str = if isLetter str then str else '-' 

codifica :: String -> String
codifica str = map confere str

firstName :: String -> String
firstName str = takeWhile (/= ' ') str


lastName :: String -> String
lastName str = dropWhile (/= ' ') str
