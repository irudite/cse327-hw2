import fol.*;
import planning.*;
import search.*;

public class EvalPlanTowers {
    private static Constant R1 = new Constant("R1");
    private static Constant R2 = new Constant("R2");
    private static Constant R3 = new Constant("R3");
    private static Constant D1 = new Constant("D1");
    private static Constant D2 = new Constant("D2");
    private static Constant D3 = new Constant("D3");

    private static Predicate On = new Predicate("On", 2);
    private static Predicate Rod = new Predicate("Rod", 1);
    private static Predicate Disk = new Predicate("Disk", 1);
    private static Predicate Clear = new Predicate("Clear", 1);

    private static ActionSchema[] actions = {
        new ActionSchema("Move",
            new Variable[] { new Variable("Disk"), new Variable("From"), new Variable("To") },
            new Literal[] {
                new Literal(Disk, new Variable("Disk")),
                new Literal(On, new Variable("Disk"), new Variable("From")),
                new Literal(Clear, new Variable("Disk")),
                new Literal(Clear, new Variable("To"))
            },
            new Atom[] {
                new Atom(On, new Variable("Disk"), new Variable("To")),
                new Atom(Clear, new Variable("From"))
            },
            new Atom[] {
                new Atom(On, new Variable("Disk"), new Variable("From")),
                new Atom(Clear, new Variable("To"))
            }
        )
    };

    public static void main(String[] args) {
        Atom[] initialState = {
            new Atom(On, D3, R1),
            new Atom(On, D2, D3),
            new Atom(On, D1, D2),
            new Atom(Rod, R1),
            new Atom(Rod, R2),
            new Atom(Rod, R3),
            new Atom(Disk, D1),
            new Atom(Disk, D2),
            new Atom(Disk, D3),
            new Atom(Clear, D1),
            new Atom(Clear, R2),
            new Atom(Clear, R3)
        };

        Literal[] goalState = {
            new Literal(new Atom(On, D3, R3)),
            new Literal(new Atom(On, D2, D3)),
            new Literal(new Atom(On, D1, D2)),
            new Literal(new Atom(Clear, D1))
        };

        PlanningProblem problem = new PlanningProblem(initialState, goalState, actions);

        SearchProblem search = new ForwardStateSpaceSearch(problem);
        SearchMethod uniformCost = new UniformCost(search);
        System.out.println("Uniform Cost Search Result:");
        uniformCost.search();
        System.out.println();

        SearchMethod greedySearch = new Greedy(search);
        System.out.println("Greedy Best First Search Result:");
        greedySearch.search();
        System.out.println();

        SearchMethod aStar = new AStar(search);
        System.out.println("A* Search Result:");
        aStar.search();
        System.out.println();
    }
}