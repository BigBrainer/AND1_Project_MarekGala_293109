package sk.martialhero.viewModel;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import sk.martialhero.model.DataOrException;
import sk.martialhero.model.Group;
import sk.martialhero.repository.CreateGroupRepository;

public class CreateGroupViewModel {

    private CreateGroupRepository createGroupRepository;
    public MutableLiveData<DataOrException<Group, Exception>> groupLiveData;

    @Inject
    public CreateGroupViewModel(CreateGroupRepository createGroupRepository) {
        this.createGroupRepository = createGroupRepository;
    }




    public void createGroupInFirestore(Group group)
    {
        groupLiveData =  createGroupRepository.createGroupInFirestore(group);
    }
}
