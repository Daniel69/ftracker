package bid.dbo.ftracker.categories;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static java.util.stream.IntStream.range;

public class CategoryOperationsTest {

    CategoryOperations operations = new CategoryOperations(){};
    CategoryFactory factory = new CategoryFactory() {};
    ThreadLocalRandom r = ThreadLocalRandom.current();
    AtomicLong secuence = new AtomicLong();

    @Test
    public void createCategoryTree() {
        List<Category> roots = range(0, 10).mapToObj(i -> build()).collect(Collectors.toList());
        final List<Category> nodes = generateTree(roots, 6);
        Collections.shuffle(nodes);

        final List<Category> tree = operations.createCategoryTree(nodes);
        assertThat(tree).hasSize(10);

        Category root1 = tree.get(0);
        int deep = 1;
        while (!root1.getChildren().isEmpty()){
            assertThat(root1.getChildren().get(0).getParentId()).isEqualTo(root1.getId());
            root1 = root1.getChildren().get(0);
            deep++;
        }
        assertThat(deep).isEqualTo(6);

    }

    private List<Category> generateTree(List<Category> roots, int deep){
        List<Category> nodes = roots;
        List<Category> tree = new LinkedList<>();
        for (int i = 0; i<deep; ++i){
            tree.addAll(nodes);
            nodes = generateChildren(nodes);
        }
        return tree;
    }

    private List<Category> generateChildren(List<Category> parents){
        return parents.stream().flatMap(root -> range(0, 4).mapToObj(i -> build(root))).collect(Collectors.toList());
    }

    private Category build(){
        return build(null);
    }

    private Category build(Category root){
        return factory.createCategory(() ->
            Category.builder()
                .parentId(root != null ? root.getId() : null)
                .name("test").build(), "s_"+secuence.incrementAndGet()).block();
    }
}