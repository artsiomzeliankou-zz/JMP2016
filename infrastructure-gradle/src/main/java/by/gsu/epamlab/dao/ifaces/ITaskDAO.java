package by.gsu.epamlab.dao.ifaces;

import java.sql.Date;
import java.util.List;

import by.gsu.epamlab.dao.exceptions.DaoSystemException;
import by.gsu.epamlab.dao.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;


public interface ITaskDAO {

    /**
     * Get task list by status for the given user.
     * @param user the user
     * @param showTaskId the identifier task Date to show
     * @return the task list for the given user
     * @throws DaoException 
     * @throws ValidationException if no such user 
     */
    public List<Task> getTaskListByUserAndStatus(User user,
			String showTaskId) throws DaoSystemException, ValidationException;
    
    /**
     * Get active task list by date for the given user.
     * @param user the user
     * @param showTaskId the identifier task Date to show
     * @return the task list for the given user
     * @throws DaoException 
     * @throws ValidationException if no such user 
     */
    public List<Task> getActiveTaskListByUserAndDate(User user, String showTaskId) throws DaoSystemException, ValidationException;
    
    
    /**
     * Create a new task.
     * @param user the user
     * @param todo the task to create
     * @return the created task
     * @throws DaoException 
     * @throws ValidationException if no such user 
     */
    public void createNewTask(User user, String title, Date dueDate) throws DaoSystemException, ValidationException;
    
    /**
     * Update a task.
     * @param user the user
     * @param arrTaskId the array of task's id's to update
     * @param updateType the new task's status to update
     * @throws ValidationException if no such user 
     */
    public void updateTask(User user, String[] arrTaskId, String updateType) throws DaoSystemException, ValidationException;

    /**
     * Add a fileName to task.
     * @param user the user
     * @param idTask the task identifier
     * @param fileName the file name to add
     * @throws ValidationException if no such user 
     */
    public void addFileName(User user, String idTask, String fileName) throws DaoSystemException, ValidationException;

    /**
     * Get String array fileNames for deleting tasks.
     * @param user the user
     * @param arrTaskId the array of task's id's to delete
     * @throws DaoException 
     * @throws ValidationException if no such user 
     */
	public String[] getfileNames(User user, String[] arrTaskId) throws DaoSystemException, ValidationException;

}
    
