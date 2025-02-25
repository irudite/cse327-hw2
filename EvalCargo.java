import fol.*;
import planning.*;
import search.*;

public class EvalCargo {
    private static Constant P1 = new Constant("P1");
    private static Constant P2 = new Constant("P2");
    private static Constant C1 = new Constant("C1");
    private static Constant C2 = new Constant("C2");
    private static Constant C3 = new Constant("C3");
    private static Constant JFK = new Constant("JFK");
    private static Constant LAX = new Constant("LAX");
    private static Constant ORD = new Constant("ORD");
    private static Constant ABE = new Constant("ABE");

    private static Predicate At = new Predicate("At", 2);
    private static Predicate In = new Predicate("In", 2);
    private static Predicate Airport = new Predicate("Airport", 1);
    private static Predicate Cargo = new Predicate("Cargo", 1);
    private static Predicate Plane = new Predicate("Plane", 1);

    private static ActionSchema[] actions = {
            new ActionSchema("Fly",
                    new Variable[] { new Variable("Plane"), new Variable("from"), new Variable("to") },
                    new Literal[] { new Literal(Plane, new Variable("Plane")),
                            new Literal(At, new Variable("Plane"), new Variable("from")),
                            new Literal(Airport, new Variable("to")) }, // Preconditions
                    new Atom[] { new Atom(At, new Variable("Plane"), new Variable("to")) }, // Add effects
                    new Atom[] { new Atom(At, new Variable("Plane"), new Variable("from")) } // Delete effects
            ),

            new ActionSchema("Load",
                    new Variable[] { new Variable("Cargo"), new Variable("Plane"), new Variable("Airport") },
                    new Literal[] { new Literal(Cargo, new Variable("Cargo")),
                            new Literal(At, new Variable("Cargo"), new Variable("Airport")),
                            new Literal(At, new Variable("Plane"), new Variable("Airport")) },
                    new Atom[] { new Atom(In, new Variable("Cargo"), new Variable("Plane")) },
                    new Atom[] { new Atom(At, new Variable("Cargo"), new Variable("Airport")) }),

            new ActionSchema("Unload",
                    new Variable[] { new Variable("Cargo"), new Variable("Plane"), new Variable("Airport") },
                    new Literal[] { new Literal(Cargo, new Variable("Cargo")),
                            new Literal(In, new Variable("Cargo"), new Variable("Plane")),
                            new Literal(At, new Variable("Plane"), new Variable("Airport")) },
                    new Atom[] { new Atom(At, new Variable("Cargo"), new Variable("Airport")) },
                    new Atom[] { new Atom(In, new Variable("Cargo"), new Variable("Plane")) })
    };

    public static void main(String[] args) {
        Atom[] initialStateOne = {
                new Atom(At, P1, LAX),
                new Atom(At, P2, JFK),
                new Atom(At, C1, LAX),
                new Atom(In, C2, P1),
                new Atom(Plane, P1),
                new Atom(Plane, P2),
                new Atom(Cargo, C1),
                new Atom(Cargo, C2),
                new Atom(Airport, JFK),
                new Atom(Airport, LAX),
                new Atom(Airport, ORD)
        };

        Atom[] initialStateTwo = {
                new Atom(At, P1, LAX),
                new Atom(At, P2, JFK),
                new Atom(At, C1, LAX),
                new Atom(In, C2, P1),
                new Atom(Plane, P1),
                new Atom(Plane, P2),
                new Atom(Cargo, C1),
                new Atom(Cargo, C2),
                new Atom(Airport, JFK),
                new Atom(Airport, LAX),
                new Atom(Airport, ORD)
        };

        Atom[] initialStateThree = {
                new Atom(At, P1, LAX),
                new Atom(At, C1, ABE),
                new Atom(At, C2, JFK),
                new Atom(At, C3, ORD),
                new Atom(Plane, P1),
                new Atom(Plane, P2),
                new Atom(Cargo, C1),
                new Atom(Cargo, C2),
                new Atom(Cargo, C3),
                new Atom(Airport, JFK),
                new Atom(Airport, LAX),
                new Atom(Airport, ORD),
                new Atom(Airport, ABE)
        };

        Literal[] goalStateOne = {
                new Literal(new Atom(At, P1, JFK)),
                new Literal(new Atom(At, C1, JFK))
        };

        Literal[] goalStateTwo = {
                new Literal(new Atom(At, P1, JFK)),
                new Literal(new Atom(At, P2, ORD)),
                new Literal(new Atom(At, C1, JFK)),
                new Literal(new Atom(In, C2, P2))
        };

        Literal[] goalStateThree = {
                new Literal(new Atom(At, C1, LAX)),
                new Literal(new Atom(At, C2, LAX)),
                new Literal(new Atom(At, C3, LAX))
        };

        PlanningProblem questionA = new PlanningProblem(initialStateOne, goalStateOne, actions);
        PlanningProblem questionB = new PlanningProblem(initialStateTwo, goalStateTwo, actions);
        PlanningProblem questionC = new PlanningProblem(initialStateThree, goalStateThree, actions);

        SearchProblem searchOne = new ForwardStateSpaceSearch(questionA);
        SearchMethod uniformCostOne = new UniformCost(searchOne);
        System.out.println("Uniform Cost Search for Question One:");
        uniformCostOne.search();
        System.out.println();

        SearchMethod greedySearchOne = new Greedy(searchOne);
        System.out.println("Greedy Best First Search for Question One:");
        greedySearchOne.search();
        System.out.println();

        SearchMethod aStarOne = new AStar(searchOne);
        System.out.println("A* Search for Question One:");
        aStarOne.search();
        System.out.println();   

        SearchProblem searchTwo = new ForwardStateSpaceSearch(questionB);
        SearchMethod uniformCostTwo = new UniformCost(searchTwo);
        System.out.println("Uniform Cost Search for Question Two:");
        uniformCostTwo.search();
        System.out.println();

        SearchMethod greedySearchTwo = new Greedy(searchTwo);
        System.out.println("Greedy Best First Search for Question Two:");
        greedySearchTwo.search();
        System.out.println();

        SearchMethod aStarTwo = new AStar(searchTwo);
        System.out.println("A* Search for Question Two:");
        aStarTwo.search();
        System.out.println();

        SearchProblem searchThree = new ForwardStateSpaceSearch(questionC);
        SearchMethod uniformCostThree = new UniformCost(searchThree);
        System.out.println("Uniform Cost Search for Question Three:");
        uniformCostThree.search();
        System.out.println();

        SearchMethod greedySearchThree = new Greedy(searchThree);
        System.out.println("Greedy Best First Search for Question Three:");
        greedySearchThree.search();
        System.out.println();

        SearchMethod aStarThree = new AStar(searchThree);
        System.out.println("A* Search for Question Three:");
        aStarThree.search();
        System.out.println();
    }
}
