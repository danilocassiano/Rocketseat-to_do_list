package br.com.danilocassiano.to_do_list.task;

import br.com.danilocassiano.to_do_list.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository <TaskModel, UUID> {

    List<TaskModel> findByIdUser(UUID idUser);

}
