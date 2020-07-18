function DFSMazeSolver(maze);
OBSTACLE = [];
MAX_X = size(maze,1);
MAX_Y = size(maze,1);
k = 1;
for i = 1 : size(maze)
    for j = 1 : size(maze)
        if(maze(i, j) == 0 || maze(i, j) == 8)
            OBSTACLE(k, 1) = i;
            OBSTACLE(k, 2) = j;
            k = k + 1;
        end
    end
end

for k = 1 : size(maze)
    for l = 1 : size(maze)
        if(maze(k, l) == 3)
            xStart = k;
            yStart = l;
        end
    end
end

for m = 1:size(maze)
    for n = 1:size(maze)
        if(maze(m, n) == 4)
            xTarget = m;
            yTarget = n;
        end
    end
end

OBST_COUNT = size(OBSTACLE, 1);
OBST_COUNT = OBST_COUNT + 1;
OBSTACLE(OBST_COUNT, :) = [xStart, yStart];

xNode = xStart;
yNode = yStart;
QUEUE = [];
QUEUE_COUNT = 1;
NoPath = 1; % assume there exists a path
havepath = 1;
goal_distance = distance(xNode, yNode, xTarget, yTarget); % cost h(n): heuristic cost of n
QUEUE(QUEUE_COUNT, :) = insert(xNode, yNode, xNode, yNode, havepath);
QUEUE(QUEUE_COUNT, 1) = 0; % What does this do?

while((xNode ~= xTarget || yNode ~= yTarget) && NoPath == 1)
    
    % expand the current node to obtain child nodes
    exp = expand(xNode, yNode, xTarget, yTarget, OBSTACLE, MAX_X, MAX_Y);
    exp_count  = size(exp, 1);
    % Update QUEUE with child nodes; exp: [X val, Y val, h(n)]
    for i = 1 : exp_count
        flag = 0;
        for j = 1 : QUEUE_COUNT
            if(exp(i, 1) == QUEUE(j, 2) && exp(i, 2) == QUEUE(j, 3))
                exp(i, 3) = 0;
                QUEUE(j, 3) = xNode;
                QUEUE(j, 4) = yNode;
                QUEUE(j, 5) = exp(i, 3);          
                flag = 1;
            end;
        end;
        if flag == 0
            QUEUE_COUNT = QUEUE_COUNT + 1;
            QUEUE(QUEUE_COUNT, :) = insert(exp(i, 1), exp(i, 2), xNode, yNode, exp(i, 3));
        end; % end of insert new element into QUEUE
    end;
    
     % A*: find the node in QUEUE with the smallest f(n), returned by min_fn
    index_min_node = min_fn(QUEUE, QUEUE_COUNT);
    if (index_min_node ~= -1)
        % set current node (xNode, yNode) to the node with minimum f(n)
        xNode = QUEUE(index_min_node, 2);
        yNode = QUEUE(index_min_node, 3);
        path_cost = QUEUE(index_min_node, 5); % cost g(n)
        % move the node to OBSTACLE
        OBST_COUNT = OBST_COUNT + 1;
        OBSTACLE(OBST_COUNT, 1) = xNode;
        OBSTACLE(OBST_COUNT, 2) = yNode;
        QUEUE(index_min_node, 1) = 0;
    else
        NoPath = 0; % there is no path!
    end;
end;

Optimal_path = [];
QUEUE_COUNT = size(QUEUE, 1);
xval = QUEUE(QUEUE_COUNT, 2);
yval = QUEUE(QUEUE_COUNT, 3);

temp = QUEUE_COUNT;         
while(((xval ~= xTarget) || (yval ~= yTarget)) && temp > 0)
    temp = temp - 1;
    xval = QUEUE(temp, 2);
    yval = QUEUE(temp, 3);
end

i = 1;
Optimal_path(i, 1) = xval;
Optimal_path(i, 2) = yval;

if ((xval == xTarget) && (yval == yTarget))
    inode = 0;
    % Traverse QUEUE and determine the parent nodes
    parent_x = QUEUE(index(QUEUE, xval, yval), 4);
    parent_y = QUEUE(index(QUEUE, xval, yval), 5);
   
    while(parent_x ~= xStart || parent_y ~= yStart)
        i = i + 1;
        Optimal_path(i, 1) = parent_x; % store nodes on the optimal path
        Optimal_path(i, 2) = parent_y;
        inode = index(QUEUE, parent_x, parent_y); % find the grandparents :)
        parent_x = QUEUE(inode, 4);
        parent_y = QUEUE(inode, 5);
    end;
    Optimal_path(i,1) = xStart-1;    % add start node to the optimal path  
    Optimal_path(i,2) = yStart;
    
    e = QUEUE_COUNT;
    for o = 2:e-1
        maze(QUEUE(o,2),QUEUE(o,3)) = 6;
        cmap = [.12 .39 1;1 1 1; 1 0 0; 1 .5 0; .65 1 0; 0 0 0; 1 0 0; 0 0 0; .65 .65 .65];
        hmo = imagesc(maze);
        colormap(cmap);
        set(gca, 'XColor', 'none');
        set(gca, 'Ycolor', 'none');
        drawnow
    end
    
    j = size(Optimal_path, 1);
    
    for o = 1:j
        maze(Optimal_path(o, 1), Optimal_path(o, 2)) = 2;
        cmap = [.12 .39 1;1 1 1; 0 0 0; 1 .5 0; .65 1 0; 0 0 0; 1 0 0; 0 0 0; .65 .65 .65];
        hmo = imagesc(maze);
        colormap(cmap);
        set(gca, 'XColor', 'none');
        set(gca, 'Ycolor', 'none');
        drawnow
    end
   
else
    pause(1);
    h = msgbox('Oops! No path exists to the Target!', 'warn');
    uiwait(h, 5);
end


