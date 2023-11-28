package xyz.xmit.silverclient.ui.statemachine;

public final class PopupSilverState
    extends BaseSilverState
{
    private Class<? extends SilverState> previousStateClass;

    public PopupSilverState(SilverApplicationContext parentContext)
    {
        super(parentContext);
    }

    public void setPreviousStateClass(Class<? extends SilverState> sourceStateClass)
    {
        this.previousStateClass = sourceStateClass;
    }

    @Override
    public void onRefreshData()
    {
    }

    @Override
    public void onHome()
    {
    }

    @Override
    public void onManageUsers()
    {
    }

    @Override
    public void onManageItems()
    {
    }

    @Override
    public void onLogout() throws SilverStateException
    {
        throw new SilverStateException("logouts denied while popup open");
    }

    @Override
    public void onRevertState()
    {
        this.getParentContext().setCurrentState(this.previousStateClass);
    }
}
