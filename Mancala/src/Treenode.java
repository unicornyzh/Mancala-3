
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/4/15.
 */
public class Treenode {
    private int parentId;
    private int selfId;
    protected Treenode parentNode;
    protected List<Treenode> childList;
    private int player;
    private int pit;
    private int marbles;
    public Treenode() {
        initChildList();
    }

    public Treenode(Treenode pnode) {
        this.getParentNode();
        initChildList();
    }
    // add a new child node
    public void addChildnode(Treenode cnode){
        initChildList();
        childList.add(cnode);
    }
    
    public Treenode findTreenodeById(int id) {  
        if (this.selfId == id)  
            return this;  
        if (childList.isEmpty() || childList == null) {  
            return null;  
        } else {  
            int childNumber = childList.size();  
            for (int i = 0; i < childNumber; i++) {  
                Treenode child = childList.get(i);  
                Treenode resultNode = child.findTreenodeById(id);  
                if (resultNode != null) {  
                    return resultNode;  
                }  
            }  
            return null;  
        }  
    }  
    public void initChildList(){
        if(childList==null)
            childList = new ArrayList<Treenode>();
    }
    /* return parent node sets */
    public List<Treenode> getElders() {
        List<Treenode> elderList = new ArrayList<Treenode>();
        Treenode parentNode = this.getParentNode();
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }
    }

    /* return juniors node sets */
    public List<Treenode> getJuniors() {
        List<Treenode> juniorList = new ArrayList<Treenode>();
        List<Treenode> childList = this.getChildList();
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                Treenode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }
            return juniorList;
        }
    }

    /* return child node sets */
    public List<Treenode> getChildList() {
        return childList;
    }

    public void setChildList(List<Treenode> childList) {
        this.childList = childList;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public Treenode getParentNode() {
        return parentNode;
    }

    public void setParentNode(Treenode parentNode) {
        this.parentNode = parentNode;
    }
    
    public void setPlayer(int player)
    {
    	this.player=player;
    }
    
    public int getPlayer()
    {
    	return this.player;
    }
    
    public void setnumberofmarbles(int n,int marbles)
    {
    	this.pit=n;
    	this.marbles=marbles;
    }
    
    public int getpit()
    {
    	return this.pit;
    }
    
    public int getmarbles()
    {
    	return marbles;
    }
}

