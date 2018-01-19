package mappers.is.web.bmw.sql;import java.util.List;import org.apache.ibatis.annotations.Param;import com.is.web.bmw.entity.sql.extend.LoginsExtend;/** * t_logins */public interface LoginsExtendViewMapper {		/**	 * 根据主键，获取一条<b>t_logins</b>记录内容、及关联数据	 * @param loginsExtend <i>LoginsExtend</i>	 * @return LoginsExtend	 */	public LoginsExtend getLoginsByPrimaryKeyWithJoin(@Param(value="logins")LoginsExtend loginsExtend);		/**	 * 根据条件，查询全部<b>t_logins</b>记录、及关联数据	 * @param loginsExtend <i>LoginsExtend</i>	 * @return List<LoginsExtend>	 */	public List<LoginsExtend> getAllLoginsBySearchWithJoin(@Param(value="logins")LoginsExtend loginsExtend);		/**	 * 根据关键字对指定列进行查询，查询全部<b>t_logins</b>记录	 * @param loginsExtendList <i>List<LoginsExtend></i> 需要查询列名称、值	 * @return List<LoginsExtend>	 */	public List<LoginsExtend> getAllLoginsBySearchKeyWithJoin(@Param(value="loginsExtendList")List<LoginsExtend> loginsExtendList);		/**	 * 根据条件，查询<b>t_logins</b>记录、及关联数据，带有分页	 * @param startIndex <i>int</i>	 * @param endIndex <i>int</i>	 * @param loginsExtend <i>LoginsExtend</i>	 * @return Pagination	 */	public List<LoginsExtend> getAllLoginsByPageWithJoin(@Param(value="startIndex")int startIndex,			  @Param(value="endIndex")int endIndex,@Param(value="logins")LoginsExtend loginsExtend);		/**	 * 根据条件，查询符合条件<b>t_logins</b>记录、及关联数据 总数	 * @param loginsExtend	 * @return	 */	public int getCountForPageWithJoin(@Param(value="logins")LoginsExtend loginsExtend);		/**	 * 根据条件，查询<b>t_logins</b>记录、及关联数据，带有分页	 * @param startIndex <i>int</i>	 * @param endIndex <i>int</i>	 * @param loginsExtendList <i>List<LoginsExtend></i> 查询列名称、值	 * @return Pagination	 */	public List<LoginsExtend> getAllLoginsByPageAndSearchKeyWithJoin(@Param(value="startIndex")int startIndex,			  @Param(value="endIndex")int endIndex,@Param(value="loginsExtendList")List<LoginsExtend> loginsExtendList);		/**	 * 根据条件，查询符合条件<b>t_logins</b>记录、及关联数据 总数	 * @param loginsExtendList <i>List<LoginsExtend></i> 查询列名称、值	 * @return	 */	public int getCountForPageAndSearchKeyWithJoin(@Param(value="loginsExtendList")List<LoginsExtend> loginsExtendList);}