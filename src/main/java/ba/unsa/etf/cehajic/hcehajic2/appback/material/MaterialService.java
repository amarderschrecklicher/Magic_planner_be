package ba.unsa.etf.cehajic.hcehajic2.appback.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository subTaskRepository;

    @Autowired
    public MaterialService(MaterialRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    public List<Material> GetAllSubTasks() {
        return subTaskRepository.findAll();
    }

    public List<Material> GetSubsForTask(Long id) {
        List<Material> subs = GetAllSubTasks();
        List<Material> matching = new ArrayList<>();

        for (int i = 0; i < subs.size(); i++)
            if (subs.get(i).getTask().getId().equals(id)) {
                matching.add(subs.get(i));}

        return matching;
    }

    public Material AddNewSubTask(Material subTask) {
        subTaskRepository.save(subTask);
        return subTask;
    }

    public Material FinishSubTask(Long id) {
        Material tOld = subTaskRepository.getById(id);
        tOld.setDone(true);
        subTaskRepository.save(tOld);
        return tOld;
    }

    public void deleteTask(Long id) {
        subTaskRepository.deleteById(id);
    }
}
