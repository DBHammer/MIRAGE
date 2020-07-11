package ecnu.db.analyzer.online.select;

import ecnu.db.utils.TouchstoneToolChainException;
import ecnu.db.utils.exception.IllegalTokenException;

/**
 * @author alan
 * 只能接受列名节点作为root子节点
 */
public class IsNullState extends BaseState {
    public IsNullState(BaseState preState, SelectNode root) {
        super(preState, root);
    }

    @Override
    public BaseState handle(Token token) throws TouchstoneToolChainException {
        SelectNode newRoot;
        switch (token.type) {
            case CANONICAL_COL_NAME:
                newRoot = new SelectNode(token);
                this.addArgument(newRoot);
                return this;
            case RIGHT_PARENTHESIS:
                preState.addArgument(this.root);
                return preState;
            default:
                throw new IllegalTokenException(token);
        }
    }

    @Override
    public void addArgument(SelectNode node) {
        root.addChild(node);
    }
}
