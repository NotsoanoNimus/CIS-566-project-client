package xyz.xmit.silverclient.ui.statemachine;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Node;
import xyz.xmit.silverclient.api.HttpApiClient;
import xyz.xmit.silverclient.api.request.GenericGetRequest;
import xyz.xmit.silverclient.models.Tag;

public final class HomeSilverState
    extends BaseContainerSilverState
{
    private boolean hasLoadedServerData = false;

    public HomeSilverState(SilverApplicationContext parentContext, Node containerNode, Node sourceEventNode)
    {
        super(parentContext, containerNode, sourceEventNode);
    }

    @Override
    protected void onLoadContainer()
    {
        if (this.hasLoadedServerData) return;

        // load data
        try {
            var resp = HttpApiClient.getInstance().GetAsync(new GenericGetRequest().setMethod("GET").setHostUrl("tag/1"), Tag.class);
            System.out.println(new ObjectMapper().writeValueAsString(resp));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.hasLoadedServerData = true;
    }

    @Override
    public void onAuthenticate() {}

    @Override
    public void onPopup() {}

    @Override
    public void onHome() {}

    @Override
    public void onManageUsers() {
        this.getParentContext().setCurrentState(ManageUsersSilverState.class);
    }

    @Override
    public void onManageItems()
    {
        this.getParentContext().setCurrentState(ManageItemsSilverState.class);
    }

    @Override
    public void onLogout()
    {
    }
}
