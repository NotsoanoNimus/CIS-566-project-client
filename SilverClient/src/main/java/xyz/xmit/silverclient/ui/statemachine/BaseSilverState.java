package xyz.xmit.silverclient.ui.statemachine;

public abstract class BaseSilverState
    implements SilverState
{
    private final SilverApplicationContext parentContext;

    public BaseSilverState(SilverApplicationContext parentContext)
    {
        this.parentContext = parentContext;
    }

    @Override
    public SilverApplicationContext getParentContext()
    {
        return this.parentContext;
    }
}
