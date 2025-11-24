maze_size(8, 8).

% maze representation
maze(0, 0, 0). maze(0, 1, 0). maze(0, 2, 0). maze(0, 3, 1). maze(0, 4, 0). maze(0, 5, 1). maze(0, 6, 0). maze(0, 7, 0).
maze(1, 0, 0). maze(1, 1, 2). maze(1, 2, 0). maze(1, 3, 0). maze(1, 4, 0). maze(1, 5, 1). maze(1, 6, 0). maze(1, 7, 0).
maze(2, 0, 0). maze(2, 1, 2). maze(2, 2, 2). maze(2, 3, 0). maze(2, 4, 1). maze(2, 5, 0). maze(2, 6, 0). maze(2, 7, 1).
maze(3, 0, 0). maze(3, 1, 0). maze(3, 2, 0). maze(3, 3, 1). maze(3, 4, 2). maze(3, 5, 0). maze(3, 6, 0). maze(3, 7, 0).
maze(4, 0, 1). maze(4, 1, 1). maze(4, 2, 0). maze(4, 3, 1). maze(4, 4, 0). maze(4, 5, 0). maze(4, 6, 2). maze(4, 7, 0).
maze(5, 0, 0). maze(5, 1, 0). maze(5, 2, 0). maze(5, 3, 0). maze(5, 4, 0). maze(5, 5, 1). maze(5, 6, 1). maze(5, 7, 0).
maze(6, 0, 0). maze(6, 1, 1). maze(6, 2, 0). maze(6, 3, 1). maze(6, 4, 1). maze(6, 5, 0). maze(6, 6, 0). maze(6, 7, 0).
maze(7, 0, 0). maze(7, 1, 0). maze(7, 2, 1). maze(7, 3, 0). maze(7, 4, 0). maze(7, 5, 0). maze(7, 6, 0). maze(7, 7, 2).

start((7, 3)).
goal((1, 1)).

move_cost(_, _, NextX, NextY, Cost) :-
    maze(NextX, NextY, Type),(
        Type == 0 -> Cost = 1 ;
        Type == 2 -> Cost = 3
        ).

% find valid moves given current position
check_moves((X, Y), ValidMoves) :-
    %findall/3
    findall((NewX, NewY), (
        % try all directions
        member((PossibleX, PossibleY), [(0,1), (1,0), (0,-1), (-1,0)]),
        NewX is X + PossibleX,
        NewY is Y + PossibleY,
        % get the maze size, and check if the move is within the boundaries, or if it is 0 (Empty)
        maze_size(Width, Height),
        NewX >= 0, NewY >= 0, NewX < Width, NewY < Height,
        maze(NewX, NewY, Type),
        (Type = 0 ; Type = 2)
    ),
    ValidMoves).


% depth_first----------------------

depth_first(Path, Energy) :-
    start(Start),
    goal(Goal),
    % this needed research
    statistics(walltime, [_ | [_]]),
    retractall(step_count(_)),
    assert(step_count(0)),
    depth_first_logic(Start, Goal, [Start], PathReverse, 0, Energy),
    reverse(PathReverse, Path),
    statistics(walltime, [_ | [TimeTaken]]),
    write('Time: '), write(TimeTaken), nl.

% goal scenario if start and goal match
depth_first_logic(Node, Node, Path, Path, Energy, Energy) :-
    reverse(Path, PathNormal),
    step_count(Steps),
    write(''), nl,
    write('Path: '), write(PathNormal), nl,
    write('Total steps: '), write(Steps), nl,
    write('Total energy: '), write(Energy), nl.

depth_first_logic(Current, Goal, Visited, Path, CurrentEnergy, Energy) :-
    check_moves(Current, ValidMoves),
    % choose a node, and go to it if it hasn't been visited yet
    member(Next, ValidMoves),
    \+ member(Next, Visited),
    write(' -> '), write('('), write(Next), write(')'),

    retract(step_count(StepCount)),
    NewStepCount is StepCount + 1,
    assert(step_count(NewStepCount)),

    Current = (X1, Y1),
    Next = (X2, Y2),
    move_cost(X1, Y1, X2, Y2, StepEnergy),
    NewEnergy is CurrentEnergy + StepEnergy,

    % add it to the visited list, and next becomes current
    depth_first_logic(Next, Goal, [Next|Visited], Path, NewEnergy, Energy).

% itterative deepening--------------------

%very similar to depth first

iterative_deepening(Path, Energy) :-
    retractall(step_count(_)),
    assert(step_count(0)),
    % number going iteratively from 1 to 200 assigned to depth
    between(1, 200, Depth),
    statistics(walltime, [_ | [_]]),
    start(Start),
    goal(Goal),
    write(''), nl,
    iterative_deepening_logic(Start, Goal, [Start], PathReverse, Depth, 0, Energy),
    reverse(PathReverse, Path), !, % cut if goal is found
    statistics(walltime, [_ | [TimeTaken]]),
    write('Time: '), write(TimeTaken), nl.

% goal scenario if start and goal match
iterative_deepening_logic(Node, Node, Path, Path, _, Energy, Energy) :-
    reverse(Path, PathNormal),
    step_count(Steps),
    write(''), nl,
    write('Path: '), write(PathNormal), nl,
    write('Total steps: '), write(Steps), nl,
    write('Total energy: '), write(Energy), nl.

iterative_deepening_logic(Current, Goal, Visited, Path, Depth, CurrentEnergy, Energy) :-
    Depth > 0,
    check_moves(Current, ValidMoves),
    member(Next, ValidMoves),
    \+ member(Next, Visited),
    DepthSmaller is Depth - 1,
    write(' -> '), write('('), write(Next), write(')'),

    retract(step_count(StepCount)),
    NewStepCount is StepCount + 1,
    assert(step_count(NewStepCount)),

    Current = (X1, Y1), Next = (X2, Y2),
    move_cost(X1, Y1, X2, Y2, StepEnergy),
    NewEnergy is CurrentEnergy + StepEnergy,

    iterative_deepening_logic(Next, Goal, [Next|Visited], Path, DepthSmaller, NewEnergy, Energy).

% astar------------------------

% manhattan distance
heuristic((X1, Y1), (X2, Y2), Dist) :-
    Dist is abs(X1 - X2) + abs(Y1 - Y2).

a_star(Path, Energy) :-
    start(Start),
    goal(Goal),
    statistics(walltime, [_ | [_]]),
    retractall(step_count(_)),
    assert(step_count(0)),
    a_star_logic([(0, 0, Start, [Start])], Goal, Path, Energy),
    statistics(walltime, [_ | [TimeTaken]]),
    write('Time: '), write(TimeTaken), nl.


% goal scenario if start and goal match
a_star_logic([(_, Energy, Node, Path)|_], Node, Path, Energy) :-
    reverse(Path, PathNormal),
    step_count(Steps),
    write(''), nl,
    write('Path: '), write(PathNormal), nl,
    StepsAdjusted is Steps - 1,
    write('Total steps: '), write(StepsAdjusted), nl,
    write('Total energy: '), write(Energy), nl.

a_star_logic(Queue, Goal, Path, Energy) :-
    Queue = [(_, CurrentEnergy, Current, Visited)|Rest],
    check_moves(Current, ValidMoves),
    findall((TotalCost, NewEnergy, Next, [Next|Visited]),(
        member(Next, ValidMoves),
        \+ member(Next, Visited),
        Current = (X1, Y1), Next = (X2, Y2),
        move_cost(X1, Y1, X2, Y2, StepEnergy),
        NewEnergy is CurrentEnergy + StepEnergy,
        heuristic(Next, Goal, HeuristicCost),
        TotalCost is NewEnergy + HeuristicCost),
        Children),
    append(Rest, Children, QueueNew),
    sort(QueueNew, QueueSorted),
    write(' -> '), write('('), write(Current), write(')'),

    retract(step_count(StepCount)),
    NewStepCount is StepCount + 1,
    assert(step_count(NewStepCount)),

    a_star_logic(QueueSorted, Goal, Path, Energy).
