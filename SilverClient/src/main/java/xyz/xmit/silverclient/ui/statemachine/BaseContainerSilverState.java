package xyz.xmit.silverclient.ui.statemachine;

import javafx.scene.Node;

public abstract class BaseContainerSilverState
    extends BaseSilverState
{
    private final Node containerNode;

    private final Node sourceEventNode;

    public BaseContainerSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext);

        this.containerNode = containerNode;
        this.sourceEventNode = sourceEventNode;

        this.onLoadContainer();
        containerNode.setVisible(true);
    }

    protected abstract void onLoadContainer();

    public Node getAssociatedContainerNode()
    {
        return this.containerNode;
    }

    public Node getSourceEventNode()
    {
        return this.sourceEventNode;
    }
}
