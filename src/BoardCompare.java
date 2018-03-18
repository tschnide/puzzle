import java.util.Comparator;

class BoardComparator implements Comparator<Solver.Node>{

    public static Comparator<Solver.Node> priorityCompare(){
        return new Comparator<Solver.Node>() {
            @Override
            public int compare(Solver.Node n1, Solver.Node n2) {
                return n1.priority - n2.priority;
            }
        };
    }

    @Override
    public int compare(Solver.Node o1, Solver.Node o2) {
        return 0;
    }
}
