type Coord = (Int, Int)
type Cell = (Coord, Maybe AgentType)
data AgentType 
    = Red       -- ^ Red agent
    | Green     -- ^ Green agent
    | Blue      -- ^ Blue agent
    deriving Eq -- Needed to compare for equality, otherwise would need to implement by ourself

ishappy :: [Cell] -> [Cell]
ishappy cs = filter happy cs
    
happy :: Cell -> Bool
happy a = if stability a > 0.3 then True else False
    
stability :: Cell -> Double
stability a = ratio (isNeighSame a)  
    
ratio ::[Bool] -> Double
ratio a = fromIntegral(length(filter (==True) a))/fromIntegral(length a)
    
isNeighSame :: Cell -> [Bool]
isNeighSame a = map (isSame a) (neighBour a)
    
isSame :: Cell -> Cell -> Bool
isSame ((a, b), ma) ((c, d), mb) = if (ma==mb) then True else False
    
neighbourAgent :: Cell -> [Cell]
neighbourAgent a = filter isAgent (neighBour a)
    
isAgent :: Cell -> Bool
isAgent (coord, ma) = if isEmpty ma then False else True
                                where isEmpty Nothing = True
                                      isEmpty _       = False
    
neighBour :: Cell -> [Cell]
neighBour ((a, b), ma) = [((a+m, b+n),ma)| m <-[-1,0,1], n <- [-1,0,1], a+m <= 20 && a+m >= 0 && b+n <= 20 && b+n >= 0 ]

isNeighbour :: [Cell] -> [Coord] -> [Cell]
isNeighbour (((a, b), ma) :xs) (c, d) = if (a == c && b == d) then ((a, b), ma):isNeighbour xs else isNeighbour xs
    
swapcells :: Cell -> Cell -> Cell
swapcells ((a, b), ma) ((c, d), Nothing) = ((a, b), Nothing)
     





