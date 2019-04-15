import Text.Printf

type Point     = (Float,Float)
type Rect      = (Point,Float,Float)


rgbPalette :: Int -> [(Int,Int,Int)]
rgbPalette n = take n $ cycle [(255,0,0),(0,255,0),(0,0,255)]

greenPalette :: Int -> Int -> [(Int,Int,Int)]
greenPalette n m = [(0,80+i*10,0) | i <- [m..n] ]

genRectsInLine :: Int -> [Rect]
genRectsInLine n  = [((m*(w+gap),0.0),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 10

genRectsInLine1 :: Int -> [Rect]
genRectsInLine1 n  = [((m*(w+gap),75.0),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 10

genRectsInLine2 :: Int -> [Rect]
genRectsInLine2 n  = [((m*(w+gap),150.0),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 10


genRectsInLine3 :: Int -> [Rect]
genRectsInLine3 n  = [((m*(w+gap),235.0),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 10

genRectsInLine4 :: Int -> [Rect]
genRectsInLine4 n  = [((m*(w+gap),310.0),w,h) | m <- [0..fromIntegral (n-1)]]
  where (w,h) = (50,50)
        gap = 10

svgRect :: Rect -> String -> String 
svgRect ((x,y),w,h) style = 
  printf "<rect x='%.3f' y='%.3f' width='%.2f' height='%.2f' style='%s' />\n" x y w h style

svgBegin :: Float -> Float -> String
svgBegin w h = printf "<svg width='%.2f' height='%.2f' xmlns='http://www.w3.org/2000/svg'>\n" w h 

svgEnd :: String
svgEnd = "</svg>"

svgStyle :: (Int,Int,Int) -> String
svgStyle (r,g,b) = printf "fill:rgb(%d,%d,%d); mix-blend-mode: screen;" r g b

svgElements :: (a -> String -> String) -> [a] -> [String] -> String
svgElements func elements styles = concat $ zipWith func elements styles

main :: IO ()
main = do
  writeFile "case1.svg" $ svgstrs 
  where svgstrs = svgBegin w h ++ svgfigs ++ svgfigs1 ++ svgfigs2 ++ svgfigs3 ++ svgfigs4 ++ svgEnd
        svgfigs = svgElements svgRect rects (map svgStyle palette)
        svgfigs1 = svgElements svgRect rects1 (map svgStyle palette1)
        svgfigs2 = svgElements svgRect rects2 (map svgStyle palette2)
        svgfigs3 = svgElements svgRect rects3 (map svgStyle palette3)
        svgfigs4 = svgElements svgRect rects4 (map svgStyle palette4)
        rects = genRectsInLine nrects
        rects1 = genRectsInLine1 nrects 
        rects2 = genRectsInLine2 nrects 
        rects3 = genRectsInLine3 nrects 
        rects4 = genRectsInLine4 nrects 
        palette = greenPalette nrects 0
        palette1 = greenPalette 13 3
        palette2 = greenPalette 16 6
        palette3 = greenPalette 19 9
        palette4 = greenPalette 22 12
        nrects = 10
        (w,h) = (1500,500) 


