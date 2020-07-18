--20029946 scysl1 Shihong LIU
module Schelling
  ( Coord
  , AgentType (..)
  , Cell

  , step
  ) where

import System.Random

-- type-definition of a coordinate in our world
type Coord = (Int, Int)

-- data definition for the agent types
data AgentType 
  = Red       -- ^ Red agent
  | Green     -- ^ Green agent
  | Blue      -- ^ Blue agent
  deriving (Eq, Show) -- Needed to compare for equality, otherwise would need to implement by ourself

-- Type-definition of a Cell: it is just a coordinate and an optional AgentType.
-- Note: the agent type is optional and is Nothing in case the cell is empty.
type Cell = (Coord, Maybe AgentType)

-- Computes one step of the Schelling Segregation model.
step :: [Cell]           -- ^ All cells of the world
     -> StdGen           -- ^ The random-number generator
     -> Double           -- ^ The ratio of equal neighbours an agent requires to be happy
     -> ([Cell], StdGen) -- ^ Result is the new list of cells and the updated random-number generator
step cs g ratio = (cs', g')
  where
    csEmpty = filter isEmpty cs
    csNonEmpty = filter (not . isEmpty) cs
    csUnhappy = filter (not . isHappy ratio cs) csNonEmpty
    csHappy = filter (isHappy ratio cs) csNonEmpty    
    (csUnhappy', csEmpty', g') = foldr moveCell ([], csEmpty, g) csUnhappy 
    cs' = csHappy ++ csUnhappy' ++ csEmpty'
    isEmpty :: Cell -> Bool
    isEmpty (_, Nothing) = True
    isEmpty _            = False

isHappy :: Double  -- ^ The satisfaction factor
        -> [Cell]  -- ^ All cells
        -> Cell    -- ^ The cell with the agent
        -> Bool    -- ^ True in case the agent is happy, False otherwise
isHappy ratio cs c = d
      where 
          moore = genNeighbours c           --[Coord]
          moore' = movecoordout moore (getCoord c)
          neighbour =  map (isNeighbour cs) moore' --[Cell]
          freenei = free neighbour
          issame = map (isSame c) freenei --[Bool]
          staBility = fromIntegral(length(filter (==True) issame))/fromIntegral(length issame)
          d = if staBility >= ratio then True else False

getCoord :: Cell -> Coord
getCoord ((a, b),ma) = (a, b)

movecoordout :: [Coord] -> Coord -> [Coord]
movecoordout [] _ = []
movecoordout ((a, b):xs) (c, d) = if (a==c&&b==d) then movecoordout xs (c, d) else (a, b):(movecoordout xs (c, d))

free :: [[Cell]] -> [Cell]
free [] = []
free (x:xs) = x ++ free xs

isSame :: Cell -> Cell -> Bool
isSame ((a, b), ma) ((c, d), mb) = if (ma==mb) then True else False

isNeighbour :: [Cell] -> Coord -> [Cell]
isNeighbour [] _ = []
isNeighbour (((a, b), ma) :xs) (c, d) = if (a == c && b == d) then ((a, b), ma):isNeighbour xs (c, d) else isNeighbour xs (c, d)

genNeighbours :: Cell -> [Coord]
genNeighbours ((a, b), ma)= [(a+m, b+n)| m <-[-1,0,1], n <- [-1,0,1], a+m <= 20 && a+m >= 0 && b+n <= 20 && b+n >= 0]
      
moveCell :: Cell                      -- ^ The unhappy agent to move
         -> ([Cell],[Cell],StdGen)  -- ^ The already move agents, the empty cells, the random-number generator
         -> ([Cell],[Cell],StdGen)  -- ^ The already move agents, the empty cells, the random-number generator
moveCell c (csm, cse, g) = (csm1, cse1, g1)
    where 
        n = length cse - 1
        (randomnumber, g') = randomR (0, n) g
        rempcell = randomempcell cse randomnumber
        swap1 = swapcells c rempcell --New empty cell
        swap2 = swapcells rempcell c --New agent cell
        (csm1, cse1, g1) = (swap2:(moveout csm c), swap1:(moveout cse rempcell), g') 
        
moveout :: [Cell] -> Cell -> [Cell]
moveout [] _ = []
moveout (x:xs) c = if x==c then moveout xs c else x:moveout xs c

randomempcell :: [Cell] -> Int -> Cell
randomempcell cse randomnumber = cse !! randomnumber

swapcells :: Cell -> Cell -> Cell
swapcells ((a, b), ma) ((c, d), mb) = ((a, b), mb)


