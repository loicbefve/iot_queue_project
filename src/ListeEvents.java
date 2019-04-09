import java.util.Vector;
public class ListeEvents {
    Vector<Event> events;

    public ListeEvents(){
        events = new Vector();
    }

    public void addEvent(Event newEvent){
        int insertIndex = 0;
        while(insertIndex < events.size()){
            Event e = (Event) events.elementAt(insertIndex);

            if (e.getInstant() > newEvent.getInstant()){
                break;
            }

            insertIndex ++;
        }
        events.insertElementAt(newEvent, insertIndex);
    }
}
