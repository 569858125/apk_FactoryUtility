//////////////////////////////////////////////////////////////////////////
/////
// TH DTV MIDDLE WARE DEFINES, PLEASE DO NOT MAKE ANY CHANGE OF THIS FILE
/////
//////////////////////////////////////////////////////////////////////////

package th.dtv;

public class MW {
//Ret Type
	static {
		System.loadLibrary("th_dtv_mw");
	}
	public static final int RET_OK = 0;
	public static final int RET_NO_CHANNEL = RET_OK+1;
	public static final int RET_NO_SIGNAL = RET_NO_CHANNEL+1;
	public static final int RET_NO_VIDEO_DATA = RET_NO_SIGNAL+1;
	public static final int RET_NO_AUDIO_DATA = RET_NO_VIDEO_DATA+1;
	public static final int RET_SCRAMBLE_CHANNEL = RET_NO_AUDIO_DATA+1;
	public static final int RET_NO_THIS_CHANNEL = RET_SCRAMBLE_CHANNEL+1;
	public static final int RET_NO_VIDEO_CHANNEL = RET_NO_THIS_CHANNEL+1;
	public static final int RET_NO_AUDIO_CHANNEL = RET_NO_VIDEO_CHANNEL+1;
	public static final int RET_SAT_EXIST = RET_NO_AUDIO_CHANNEL+1;
	public static final int RET_TP_EXIST = RET_SAT_EXIST+1;
	public static final int RET_INVALID_SAT = RET_TP_EXIST+1;
	public static final int RET_INVALID_AREA = RET_INVALID_SAT+1;
	public static final int RET_INVALID_TP = RET_INVALID_AREA+1;
	public static final int RET_BOOK_EVENT_INVALID = RET_INVALID_TP+1;
	public static final int RET_BOOK_CONFLICT_EVENT = RET_BOOK_EVENT_INVALID+1;
	public static final int RET_EVENT_NOT_FOUND = RET_BOOK_CONFLICT_EVENT+1;
	public static final int RET_INVALID_REGION_POS = RET_EVENT_NOT_FOUND+1;
	public static final int RET_INVALID_SAT_INDEX = RET_INVALID_REGION_POS+1;
	public static final int RET_INVALID_AREA_INDEX = RET_INVALID_SAT_INDEX+1;
	public static final int RET_INVALID_TP_INDEX = RET_INVALID_AREA_INDEX+1;
	public static final int RET_INVALID_RECORD_INDEX = RET_INVALID_TP_INDEX+1;
	public static final int RET_MEMORY_ERROR = RET_INVALID_RECORD_INDEX+1;
	
	public static final int RET_INVALID_TYPE = RET_MEMORY_ERROR+1;
	public static final int RET_INVALID_TELETEXT_COLOR = RET_INVALID_TYPE+1;
	public static final int RET_INVALID_DATA_LENGTH = RET_INVALID_TELETEXT_COLOR+1;
	public static final int RET_INVALID_SAT_INDEX_LIST = RET_INVALID_DATA_LENGTH+1;
	public static final int RET_INVALID_AREA_INDEX_LIST = RET_INVALID_SAT_INDEX_LIST+1;
	public static final int RET_INVALID_TP_INDEX_LIST = RET_INVALID_AREA_INDEX_LIST+1;
	public static final int RET_INVALID_RECORD_INDEX_LIST = RET_INVALID_TP_INDEX_LIST+1;
	public static final int RET_INVALID_OBJECT = RET_INVALID_RECORD_INDEX_LIST+1;
	public static final int RET_INVALID_SERVICE = RET_INVALID_OBJECT+1;
	public static final int RET_INVALID_BOOK_TYPE = RET_INVALID_SERVICE+1;
	public static final int RET_INVALID_BOOK_MODE = RET_INVALID_BOOK_TYPE+1;
	public static final int RET_INVALID_DATE_TIME = RET_INVALID_BOOK_MODE+1;
	
	public static final int RET_ALLOC_OBJECT_FAILED = RET_INVALID_DATE_TIME+1;
	public static final int RET_GET_OBJECT_FAILED = RET_ALLOC_OBJECT_FAILED+1;
	public static final int RET_GET_BUFFER_FAILED = RET_GET_OBJECT_FAILED+1;
	public static final int RET_SYSTEM_ERROR = RET_GET_BUFFER_FAILED+1;
	public static final int RET_GET_JAVA_METHOD_FAILED = RET_SYSTEM_ERROR+1;
	public static final int RET_FUNCTION_NOT_SUPPORTTED = RET_GET_JAVA_METHOD_FAILED+1;

	public static final int RET_ALREADY_RECORDING = RET_FUNCTION_NOT_SUPPORTTED+1;
	public static final int RET_NO_THIS_RECORD = RET_ALREADY_RECORDING+1;
	public static final int RET_PLAYBACK_FAILED = RET_NO_THIS_RECORD+1;	
	public static final int RET_SERVICE_LOCK = RET_PLAYBACK_FAILED+1;	
	public static final int RET_NO_DATA = RET_SERVICE_LOCK+1;	
	
	public static final int RET_REC_ERR_CANNOT_CREATE_THREAD = RET_NO_DATA+1;
	public static final int RET_REC_ERR_BUSY = RET_REC_ERR_CANNOT_CREATE_THREAD+1;
	public static final int RET_REC_ERR_CANNOT_OPEN_FILE = RET_REC_ERR_BUSY+1;
	public static final int RET_REC_ERR_CANNOT_WRITE_FILE = RET_REC_ERR_CANNOT_OPEN_FILE+1;
	public static final int RET_REC_ERR_CANNOT_ACCESS_FILE = RET_REC_ERR_CANNOT_WRITE_FILE+1;
	public static final int RET_REC_ERR_FILE_TOO_LARGE = RET_REC_ERR_CANNOT_ACCESS_FILE+1;
	public static final int RET_REC_ERR_DVR = RET_REC_ERR_FILE_TOO_LARGE+1;
	
//Event Type	
	public static final int EVENT_TYPE_PLAY_STATUS = 0;
	public static final int EVENT_TYPE_SEARCH_STATUS = EVENT_TYPE_PLAY_STATUS+1; /*force callback , not need register*/
	public static final int EVENT_TYPE_TUNER_STATUS = EVENT_TYPE_SEARCH_STATUS+1;
	public static final int EVENT_TYPE_TUNER_STATUS_2 = EVENT_TYPE_TUNER_STATUS+1;
	public static final int EVENT_TYPE_DATE_TIME = EVENT_TYPE_TUNER_STATUS_2+1;
	public static final int EVENT_TYPE_EPG_CURRENT_NEXT_UPDATE = EVENT_TYPE_DATE_TIME+1;	
	public static final int EVENT_TYPE_EPG_SCHEDULE_UPDATE = EVENT_TYPE_EPG_CURRENT_NEXT_UPDATE+1;	
	public static final int EVENT_TYPE_TELETEXT_SUBTITLE_UPDATE = EVENT_TYPE_EPG_SCHEDULE_UPDATE+1; /*force callback , not need register*/
	public static final int EVENT_TYPE_PVR_STATUS = EVENT_TYPE_TELETEXT_SUBTITLE_UPDATE+1; /*force callback , not need register*/	
	public static final int EVENT_TYPE_BOOK_STATUS = EVENT_TYPE_PVR_STATUS+1; /*force callback , not need register, ** Only for DtvService ** */
	public static final int EVENT_TYPE_BOOK_LIST_UPDATE = EVENT_TYPE_BOOK_STATUS+1;
	public static final int EVENT_TYPE_SERVICE_STATUS_UPDATE = EVENT_TYPE_BOOK_LIST_UPDATE+1; /*force callback , not need register*/
	
	public static final int SEARCH_STATUS_SEARCH_START = 0;
	public static final int SEARCH_STATUS_UPDATE_CHANNEL_LIST = SEARCH_STATUS_SEARCH_START+1;	
	public static final int SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_TUNNING_INFO = SEARCH_STATUS_UPDATE_CHANNEL_LIST+1;	
	public static final int SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PROGRESS = SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_TUNNING_INFO+1;	
	public static final int SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PARAMS_INFO = SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PROGRESS+1;	
	public static final int SEARCH_STATUS_UPDATE_DVB_S_PARAMS_INFO = SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PARAMS_INFO+1;	
	public static final int SEARCH_STATUS_UPDATE_DVB_T_PARAMS_INFO = SEARCH_STATUS_UPDATE_DVB_S_PARAMS_INFO+1;	
	public static final int SEARCH_STATUS_SAVE_DATA_START = SEARCH_STATUS_UPDATE_DVB_T_PARAMS_INFO+1;	
	public static final int SEARCH_STATUS_SAVE_DATA_END = SEARCH_STATUS_SAVE_DATA_START+1;	
	public static final int SEARCH_STATUS_SEARCH_END = SEARCH_STATUS_SAVE_DATA_END+1;	

	public static final int PLAY_STATUS_OK = 0;
	public static final int PLAY_STATUS_NO_CHANNEL = PLAY_STATUS_OK+1;	
	public static final int PLAY_STATUS_NO_SIGNAL = PLAY_STATUS_NO_CHANNEL+1;	
	public static final int PLAY_STATUS_NO_VIDEO = PLAY_STATUS_NO_SIGNAL+1;	
	public static final int PLAY_STATUS_NO_AUDIO = PLAY_STATUS_NO_VIDEO+1;	
	public static final int PLAY_STATUS_SCRAMBLE = PLAY_STATUS_NO_AUDIO+1;		
	public static final int PLAY_STATUS_MOTOR_MOVING = PLAY_STATUS_SCRAMBLE+1;		
	
	public static final int PVR_RECORD_STATUS_START = 0;
	public static final int PVR_RECORD_STATUS_END = PVR_RECORD_STATUS_START+1;	
	public static final int PVR_RECORD_STATUS_UPDATE_TIME = PVR_RECORD_STATUS_END+1;	
	public static final int PVR_RECORD_STATUS_ERROR = PVR_RECORD_STATUS_UPDATE_TIME+1;	
	
	public static final int PVR_PLAYBACK_STATUS_INIT = PVR_RECORD_STATUS_ERROR+1;	
	public static final int PVR_PLAYBACK_STATUS_EXIT = PVR_PLAYBACK_STATUS_INIT+1;	
	public static final int PVR_PLAYBACK_STATUS_PLAY = PVR_PLAYBACK_STATUS_EXIT+1;	
	public static final int PVR_PLAYBACK_STATUS_PAUSE = PVR_PLAYBACK_STATUS_PLAY+1;	
	public static final int PVR_PLAYBACK_STATUS_FFFB = PVR_PLAYBACK_STATUS_PAUSE+1;	
	public static final int PVR_PLAYBACK_STATUS_SEARCH_OK = PVR_PLAYBACK_STATUS_FFFB+1;	
	
	public static final int PVR_TIMESHIFT_STATUS_INIT = PVR_PLAYBACK_STATUS_SEARCH_OK+1;	
	public static final int PVR_TIMESHIFT_STATUS_EXIT = PVR_TIMESHIFT_STATUS_INIT+1;	
	public static final int PVR_TIMESHIFT_STATUS_PLAY = PVR_TIMESHIFT_STATUS_EXIT+1;	
	public static final int PVR_TIMESHIFT_STATUS_PAUSE = PVR_TIMESHIFT_STATUS_PLAY+1;	
	public static final int PVR_TIMESHIFT_STATUS_FFFB = PVR_TIMESHIFT_STATUS_PAUSE+1;	
	public static final int PVR_TIMESHIFT_STATUS_SEARCH_OK = PVR_TIMESHIFT_STATUS_FFFB+1;	

	public static final int BOOK_STATUS_ACTION = 0;	
	public static final int BOOK_STATUS_NOTIFY = BOOK_STATUS_ACTION+1;	

	public static final int SERVICE_STATUS_NAME_UPDATE = 0;	
	public static final int SERVICE_STATUS_PARAMS_UPDATE = SERVICE_STATUS_NAME_UPDATE+1;	
	public static final int SERVICE_STATUS_RESOLUTION_TYPE_UPDATE = SERVICE_STATUS_PARAMS_UPDATE+1;	

	public static final int EPG_EVENT_PLAYING_STATUS_EXPIRED = 0;
	public static final int EPG_EVENT_PLAYING_STATUS_PLAYING = EPG_EVENT_PLAYING_STATUS_EXPIRED+1;	
	public static final int EPG_EVENT_PLAYING_STATUS_NEW = EPG_EVENT_PLAYING_STATUS_PLAYING+1;	

	public static final int TELETEXT_SUBTITLE_UPDATE = 0;
	public static final int TELETEXT_SUB_PAGE_UPDATE = TELETEXT_SUBTITLE_UPDATE+1;	

	public static final int SWITCH_CHANNEL_MODE_BLANK = 0;
	public static final int SWITCH_CHANNEL_MODE_FREEZE = SWITCH_CHANNEL_MODE_BLANK+1;	

//
	public static final int LNB_POL_V = 0;	
	public static final int LNB_POL_H = 1;	

	public static final int LNB_DISEQC_PORT_1 = 0;	
	public static final int LNB_DISEQC_PORT_2 = LNB_DISEQC_PORT_1+1;	
	public static final int LNB_DISEQC_PORT_3 = LNB_DISEQC_PORT_2+1;	
	public static final int LNB_DISEQC_PORT_4 = LNB_DISEQC_PORT_3+1;	
	public static final int LNB_DISEQC_PORT_5 = LNB_DISEQC_PORT_4+1;	
	public static final int LNB_DISEQC_PORT_6 = LNB_DISEQC_PORT_5+1;	
	public static final int LNB_DISEQC_PORT_7 = LNB_DISEQC_PORT_6+1;	
	public static final int LNB_DISEQC_PORT_8 = LNB_DISEQC_PORT_7+1;	
	public static final int LNB_DISEQC_PORT_9 = LNB_DISEQC_PORT_8+1;	
	public static final int LNB_DISEQC_PORT_10 = LNB_DISEQC_PORT_9+1;	
	public static final int LNB_DISEQC_PORT_11 = LNB_DISEQC_PORT_10+1;	
	public static final int LNB_DISEQC_PORT_12 = LNB_DISEQC_PORT_11+1;	
	public static final int LNB_DISEQC_PORT_13 = LNB_DISEQC_PORT_12+1;	
	public static final int LNB_DISEQC_PORT_14 = LNB_DISEQC_PORT_13+1;	
	public static final int LNB_DISEQC_PORT_15 = LNB_DISEQC_PORT_14+1;	
	public static final int LNB_DISEQC_PORT_16 = LNB_DISEQC_PORT_15+1;	
	public static final int LNB_DISEQC_PORT_COUNT = LNB_DISEQC_PORT_16+1;	

	public static final int LNB_DISEQC_TYPE_NONE = 0;	
	public static final int LNB_DISEQC_TYPE_V_1_0 = LNB_DISEQC_TYPE_NONE+1;	
	public static final int LNB_DISEQC_TYPE_V_1_1 = LNB_DISEQC_TYPE_V_1_0+1;	

	public static final int LNB_MOTOR_TYPE_NONE = 0;	
	public static final int LNB_MOTOR_TYPE_V_1_2 = LNB_MOTOR_TYPE_NONE+1;	
	public static final int LNB_MOTOR_TYPE_V_1_3 = LNB_MOTOR_TYPE_V_1_2+1;	

	public static final int SAT_POSITION_EAST = 0;	
	public static final int SAT_POSITION_WEST = 1;	

	public static final int LNB_TYPE_SINGLE = 0;
	public static final int LNB_TYPE_C_DOUBLE_5150_5750 = LNB_TYPE_SINGLE+1;
	public static final int LNB_TYPE_C_DOUBLE_5750_5150 = LNB_TYPE_C_DOUBLE_5150_5750+1;
	public static final int LNB_TYPE_UNIVERSAL = LNB_TYPE_C_DOUBLE_5750_5150+1;	
	public static final int LNB_TYPE_UNICABLE = LNB_TYPE_UNIVERSAL+1;	

	public static final int LNB_SCR_POSTION_A = 0;
	public static final int LNB_SCR_POSTION_B = LNB_SCR_POSTION_A+1;
	
	public static final int UNICABLE_TYPE_SINGLE_SAT_4_SCR = 0;
	public static final int UNICABLE_TYPE_SINGLE_SAT_8_SCR = UNICABLE_TYPE_SINGLE_SAT_4_SCR+1;	
	public static final int UNICABLE_TYPE_DUAL_SAT_4_SCR = UNICABLE_TYPE_SINGLE_SAT_8_SCR+1;	
	public static final int UNICABLE_TYPE_DUAL_SAT_8_SCR = UNICABLE_TYPE_DUAL_SAT_4_SCR+1;	

	public static final int SEARCH_TYPE_BLIND = 0;
	public static final int SEARCH_TYPE_AUTO = SEARCH_TYPE_BLIND+1;	
	public static final int SEARCH_TYPE_MANUAL = SEARCH_TYPE_AUTO+1;	
	
	public static final int BOOK_TYPE_NONE = 0;
	public static final int BOOK_TYPE_PLAY = BOOK_TYPE_NONE+1;
	public static final int BOOK_TYPE_RECORD = BOOK_TYPE_PLAY+1;	
	public static final int BOOK_TYPE_STANDBY = BOOK_TYPE_RECORD+1;	
	public static final int BOOK_TYPE_POWER_ON = BOOK_TYPE_STANDBY+1;	
	
	public static final int BOOK_MODE_ONCE = 0;
	public static final int BOOK_MODE_DAILY = BOOK_MODE_ONCE+1;
	public static final int BOOK_MODE_WEEKLY = BOOK_MODE_DAILY+1;	
	public static final int BOOK_MODE_MONTHLY = BOOK_MODE_WEEKLY+1;	
	public static final int BOOK_MODE_WORKDAY = BOOK_MODE_MONTHLY+1;	
	public static final int BOOK_MODE_WEEKEND = BOOK_MODE_WORKDAY+1;	

	public static final int BOOK_EVENT_CONFLICT_MODE_START_TIME = 0;
	public static final int BOOK_EVENT_CONFLICT_MODE_PERIOD = BOOK_EVENT_CONFLICT_MODE_START_TIME+1;	

	public static final int TV_CH = 0;
	public static final int RADIO_CH = TV_CH+1;	
	
	public static final int VIDEO_STREAM_TYPE_MPEG2 = 0;
	public static final int VIDEO_STREAM_TYPE_H264 = VIDEO_STREAM_TYPE_MPEG2+1;	
	public static final int VIDEO_STREAM_TYPE_MPEG4 = VIDEO_STREAM_TYPE_H264+1;	
	public static final int VIDEO_STREAM_TYPE_VC1 = VIDEO_STREAM_TYPE_MPEG4+1;	
	public static final int VIDEO_STREAM_TYPE_H265 = VIDEO_STREAM_TYPE_VC1+1;	
 
	public static final int AUDIO_STREAM_TYPE_MPEG1 = 0;
	public static final int AUDIO_STREAM_TYPE_MPEG2 = AUDIO_STREAM_TYPE_MPEG1+1;	
	public static final int AUDIO_STREAM_TYPE_AC3 = AUDIO_STREAM_TYPE_MPEG2+1;	
	public static final int AUDIO_STREAM_TYPE_EAC3 = AUDIO_STREAM_TYPE_AC3+1;	
	public static final int AUDIO_STREAM_TYPE_AAC = AUDIO_STREAM_TYPE_EAC3+1;	
	public static final int AUDIO_STREAM_TYPE_AAC_LATM = AUDIO_STREAM_TYPE_AAC+1;	
	public static final int AUDIO_STREAM_TYPE_DTS = AUDIO_STREAM_TYPE_AAC_LATM+1;	
	
	public static final int TELETEXT_COLOR_RED = 0;
	public static final int TELETEXT_COLOR_GREEN = TELETEXT_COLOR_RED+1;	
	public static final int TELETEXT_COLOR_YELLOW = TELETEXT_COLOR_GREEN+1;	
	public static final int TELETEXT_COLOR_BLUE = TELETEXT_COLOR_YELLOW+1;	

	public static final int TELETEXT_TYPE_INITIAL_TELETEXT_PAGE = 1;
	public static final int TELETEXT_TYPE_TEXLETEXT_SUBTITLE_PAGE = TELETEXT_TYPE_INITIAL_TELETEXT_PAGE+1;
	public static final int TELETEXT_TYPE_ADDITIONAL_INFORMATION_PAGE = TELETEXT_TYPE_TEXLETEXT_SUBTITLE_PAGE+1;
	public static final int TELETEXT_TYPE_PROGRAMME_SCHEDULE_PAGE = TELETEXT_TYPE_ADDITIONAL_INFORMATION_PAGE+1;
	public static final int TELETEXT_TYPE_TEXLETEXT_SUBTITLE_PAGE_FOR_HEARING_IMPAIRED_PEOPLE = TELETEXT_TYPE_PROGRAMME_SCHEDULE_PAGE+1;	

	public static final int SUBTITLING_TYPE_EBU_TELETEXT_SUBTITLE = 0x01;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_NO_RATIO = 0x10;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_4_3_RATIO = 0x11;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_16_9_RATIO = 0x12;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_2_21_1_RATIO = 0x13;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_HARD_HEARING_NO_RATIO = 0x20;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_HARD_HEARING_4_3_RATIO = 0x21;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_HARD_HEARING_16_9_RATIO = 0x22;
	public static final int SUBTITLING_TYPE_DVB_SUBTITLE_HARD_HEARING_2_21_1_RATIO = 0x23;

	public static final int SERVICE_SORT_TYPE_A_TO_Z = 0x01;
	public static final int SERVICE_SORT_TYPE_SATELLITE = 0x02;
	public static final int SERVICE_SORT_TYPE_TRANSPONDER = 0x04;
	public static final int SERVICE_SORT_TYPE_CAS = 0x08;

	public static final int SEARCH_SERVICE_TYPE_ALL = 0;
	public static final int SEARCH_SERVICE_TYPE_FTA_ONLY = SEARCH_SERVICE_TYPE_ALL+1;

	public static final int SEARCH_TV_TYPE_ALL = 0;
	public static final int SEARCH_TV_TYPE_TV_ONLY = SEARCH_TV_TYPE_ALL+1;
	public static final int SEARCH_TV_TYPE_RADIO_ONLY = SEARCH_TV_TYPE_TV_ONLY+1;
	
	public static final int LOCATION_EAST = 0;
	public static final int LOCATION_WEST = LOCATION_EAST+1;
	
	public static final int HEMISPHERE_SOUTH = 0;
	public static final int HEMISPHERE_NORTH = HEMISPHERE_SOUTH+1;

	public static final int MAIN_TUNER_INDEX = 0;
	public static final int SECOND_TUNER_INDEX = MAIN_TUNER_INDEX+1;

	public static final int SYSTEM_TYPE_SAT = 0;
	public static final int SYSTEM_TYPE_TER = SYSTEM_TYPE_SAT+1;

	public static final int SYSTEM_TUNER_CONFIG_S = 0;
	public static final int SYSTEM_TUNER_CONFIG_T = SYSTEM_TUNER_CONFIG_S+1;
	public static final int SYSTEM_TUNER_CONFIG_S_S = SYSTEM_TUNER_CONFIG_T+1;
	public static final int SYSTEM_TUNER_CONFIG_ST = SYSTEM_TUNER_CONFIG_S_S+1;
	public static final int SYSTEM_TUNER_CONFIG_ST_S = SYSTEM_TUNER_CONFIG_ST+1;

	public static final int FRONT_END_TYPE_Undef = 0;
	public static final int FRONT_END_TYPE_DVBS = FRONT_END_TYPE_Undef+1;
	public static final int FRONT_END_TYPE_DVBS_2 = FRONT_END_TYPE_DVBS+1;
	public static final int FRONT_END_TYPE_TER = FRONT_END_TYPE_DVBS_2+1; //for DVB T / DTMB / ATSC / ISDB T, etc
	public static final int FRONT_END_TYPE_DVBT_2 = FRONT_END_TYPE_TER+1;

	public static final int DYNAMIC_SERVICE_UPDATE_MODE_OFF = 0;
	public static final int DYNAMIC_SERVICE_UPDATE_MODE_PARAMETERS = DYNAMIC_SERVICE_UPDATE_MODE_OFF+1;
	public static final int DYNAMIC_SERVICE_UPDATE_MODE_SERVICE = DYNAMIC_SERVICE_UPDATE_MODE_PARAMETERS+1;

	public static final int PVR_RECORD_MODE_CURRENT_SERVICE = 0;
	public static final int PVR_RECORD_MODE_CURRENT_SERVICE_WITH_SI_PSI = PVR_RECORD_MODE_CURRENT_SERVICE+1;
	public static final int PVR_RECORD_MODE_WHOLE_TS = PVR_RECORD_MODE_CURRENT_SERVICE_WITH_SI_PSI+1;

	public static final int ATSC_SYSTEM_TYPE_AIR = 0;
	public static final int ATSC_SYSTEM_TYPE_CABLE = ATSC_SYSTEM_TYPE_AIR+1;
	
	
//tuner types
	public static final int TUNER_TYPE_UNDEFINED = 0;
	
	public static final int TUNER_TYPE_DVB_S = 100;
	public static final int TUNER_TYPE_DVB_S2 = 101;
	public static final int TUNER_TYPE_ABS_S = 102;
	public static final int TUNER_TYPE_ISDB_T = 103;
	public static final int TUNER_TYPE_DVB_T = 104;
	public static final int TUNER_TYPE_DVB_T2 = 105;
	public static final int TUNER_TYPE_DVB_C = 106;
	public static final int TUNER_TYPE_DTMB = 107;
	public static final int TUNER_TYPE_ATSC = 108;
	

// db
	//service
	
	/*	Return Value : total service count 
    */
	public static native int db_get_total_service_count();

	/*	Return Value : total service count of appointed service type
	*/
	public static native int db_get_service_count(int service_type);

	/*	Return Value : RET_OK
					   RET_ALLOC_OBJECT_FAILED
	                   RET_NO_CHANNEL 
					   RET_INVALID_OBJECT
	*/
	public static native int db_get_current_service_info(mw_data.service o_service_clazz);
	
	/*	Return Value : RET_OK
					   RET_ALLOC_OBJECT_FAILED
	                   RET_NO_THIS_CHANNEL 
					   RET_INVALID_OBJECT
	*/
	public static native int db_get_service_info(int serviceType, int serviceIndex, mw_data.service o_service_clazz);
	
	/*	Return Value : RET_OK
					   RET_ALLOC_OBJECT_FAILED
	                   RET_NO_THIS_CHANNEL 
					   RET_INVALID_OBJECT
	*/
	public static native int db_get_service_info_by_channel_number(int chnum, mw_data.service o_service_clazz);

	/*	Return Value : find match service count 
    */
	public static native int db_get_find_match_service_count();

	/*	Return Value : None
    */
	public static native void db_free_find_match_service();
	
	
	/*	Return Value : service index of match service, -1 if failed
    */
	public static native int db_get_find_match_service_index(int matchServicePos);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/
	public static native int db_find_match_service(int serviceType, String serviceName, boolean bCaseSensitive);

	

	/*	Return Value : total region count that the regions have channels
    */
	public static native int db_get_service_region_count();
	
	/*	Return Value : current service region select pos
    */
	public static native int db_get_service_region_current_pos();
	
	/*	Return Value : RET_OK
	                   RET_INVALID_REGION_POS 
					   RET_INVALID_OBJECT
	*/
	public static native int db_get_service_region_info(int serviceRegionPos, mw_data.region_info o_region_clazz);
	
	/*	Return Value : true , true means service region current index changed, the caller app should replay current service
	                          due to current service type or index maybe changed and should also refresh channel list display
	                   false, false means service region current index not changed
    */
	public static native boolean db_set_service_region_current_pos(int serviceRegionPos);

	/*	Return Value : total fav count that the favs have channels
    */
	public static native int db_get_service_available_fav_group_count();
	
	/*	Return Value : current service fav select pos
    */
	public static native int db_get_service_available_fav_group_current_pos();

	/*	Return Value : true , true means service fav current index changed, the caller app should replay current service
	                          due to current service type or index maybe changed and should also refresh channel list display
	                   false, false means service fav current index not changed
    */
	public static native boolean db_set_service_available_fav_group_current_pos(int serviceFavPos);

	/*	Return Value : fav group index of appointted fav group pos
    */
	public static native int db_get_service_available_fav_group_index(int serviceFavPos);
	
	/*	Return Value : true , true means service fav current index changed, the caller app should replay current service
	                          due to current service type or index maybe changed and should also refresh channel list display
	                   false, false means service fav current index not changed
    */
	public static native boolean db_set_service_total_fav_group_current_index(int index);
	
	/*	Return Value : current total fav group index
    */
	public static native int db_get_service_total_fav_group_current_index();
	
	/*	Return Value : true , true means service list is in fav mode
	                   false, false means service list is in region mode
    */
	public static native boolean db_check_service_in_fav_mode();
	
	/*	Return Value : None
    */
	public static native void db_service_set_in_fav_mode();
	
	/*	Return Value : None
    */
	public static native void db_service_set_in_region_mode();
	
	/*	Return Value : None
    */
	public static native void db_service_set_in_region_mode_with_service_type_index(int serviceType, int serviceIndex);

	
	//sat,tp	
	
	/*	Return Value : total sat count
    */
	public static native int db_dvb_s_get_sat_count(int tunerIndex);
	
	/*	Return Value : total selected sat count
    */
	public static native int db_dvb_s_get_selected_sat_count(int tunerIndex);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
					   RET_INVALID_SAT
	*/
	public static native int db_dvb_s_get_selected_sat_info(int tunerIndex, int selectSatPos, mw_data.dvb_s_sat o_sat_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
					   RET_INVALID_SAT
	*/
	public static native int db_dvb_s_get_current_sat_info(int tunerIndex, mw_data.dvb_s_sat o_sat_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_s_get_sat_info(int tunerIndex, int satIndex, mw_data.dvb_s_sat o_sat_clazz);
	
	/*	Return Value : tp count of the appointed satIndex
					   RET_INVALID_SAT_INDEX
	*/	
	public static native int db_dvb_s_get_tp_count(int tunerIndex, int satIndex);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
					   RET_INVALID_TP
	*/	
	public static native int db_dvb_s_get_current_tp_info(int tunerIndex, mw_data.dvb_s_tp o_tp_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_s_get_tp_info(int tunerIndex, int satIndex, int tpIndex, mw_data.dvb_s_tp o_tp_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
	*/	
	public static native int db_dvb_s_set_current_sat_tp(int tunerIndex, int satIndex, int tpIndex);

	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_s_set_sat_info(int tunerIndex, mw_data.dvb_s_sat i_sat_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_s_set_tp_info(int tunerIndex, mw_data.dvb_s_tp i_tp_clazz);
	
	/*	Return Value : true , true means save sat tp data succeed
	                   false, false means save sat tp data failed
    */
	public static native boolean db_dvb_s_save_sat_tp(int tunerIndex);
	
	/*	Return Value : true , true means save sat service data succeed
	                   false, false means save sat service data failed
    */
	public static native boolean db_save_service();
	
	/*	Return Value : true , true means delete all service data succeed
	                   false, false means delete all service data failed
    */
	public static native boolean db_delete_all_service();
	
	/*	Return Value : RET_OK
					   RET_SAT_EXIST
					   RET_MEMORY_ERROR
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_s_add_sat(String sat_name, int position, int degree_dec, int degree_point);
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_SAT_EXIST
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_s_update_sat(int satIndex, String sat_name, int position, int degree_dec, int degree_point);
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_SAT_INDEX_LIST
					   RET_MEMORY_ERROR
	*/	
	public static native int db_dvb_s_delete_sat(int[] satIndexList);


	/*	Return Value : true , true means there has service of unselect sat
					   false , false means there has not service of unselect sat
	*/	
	public static native boolean db_dvb_s_check_sat_has_service(int satIndex);
	
	/*	Return Value : None
	*/	
	public static native void db_dvb_s_delete_unselect_service();
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_MEMORY_ERROR
					   RET_TP_EXIST
	*/	
	public static native int db_dvb_s_add_tp(int satIndex, int frequency, int symbolrate, int polarization);

	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
					   RET_TP_EXIST
	*/	
	public static native int db_dvb_s_update_tp(int satIndex, int tpIndex, int frequency, int symbolrate, int polarization);

	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_TP_INDEX_LIST
					   RET_MEMORY_ERROR
	*/	
	public static native int db_dvb_s_delete_tp(int satIndex, int[] tpIndexList);


	//scr 
	
	/*	Return Value : band Pass Frequency of the appointed unicableType and scrNumber
    */
	public static native int db_get_scr_bp_frequency(int unicableType, int scrNumber);

	//area, tp
	
	/*	Return Value : total area count
    */
	public static native int db_dvb_t_get_area_count();	

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
					   RET_INVALID_AREA
	*/
	public static native int db_dvb_t_get_current_area_info(mw_data.dvb_t_area o_area_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_t_get_area_info(int areaIndex, mw_data.dvb_t_area o_area_clazz);
	
	/*	Return Value : tp count of the appointed areaIndex
					   RET_INVALID_AREA_INDEX
	*/	
	public static native int db_dvb_t_get_tp_count(int areaIndex);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
					   RET_INVALID_TP
	*/	
	public static native int db_dvb_t_get_current_tp_info(mw_data.dvb_t_tp o_tp_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_t_get_tp_info(int areaIndex, int tpIndex, mw_data.dvb_t_tp o_tp_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_TP_INDEX
	*/	
	public static native int db_dvb_t_set_current_area_tp(int areaIndex, int tpIndex);
	
	/*	Return Value : RET_OK
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int db_dvb_t_set_tp_info(mw_data.dvb_t_tp i_tp_clazz);

	/*	Return Value : true , true means save area tp data succeed
	                   false, false means save area tp data failed
    */
	public static native boolean db_dvb_t_save_area_tp();
	
	/*	Return Value : RET_OK
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_TP_INDEX
					   RET_TP_EXIST
	*/	
	public static native int db_dvb_t_update_tp(int areaIndex, int tpIndex, int frequency, int bandwidth);

	
	/*	Return Value : None
    */
	public static native void db_load_default_data();

	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
					   RET_INVALID_OBJECT
	*/	
	public static native int db_export_user_data(String storage_mount_dir);

	/*	Return Value : RET_OK
					   RET_NO_DATA
					   RET_SYSTEM_ERROR
					   RET_INVALID_OBJECT
	*/	
	public static native int db_import_user_data(String storage_mount_dir);

// ts player	

	//play
	
	/*	Return Value : None
    */
	public static native void ts_player_require_play_status();

	/*	Return Value : play status of current ts player
	                        value range : from PLAY_STATUS_OK to PLAY_STATUS_SCRAMBLE
    */
	public static native int ts_player_get_play_status();

	/*	Return Value : None
    */
	public static native void ts_player_stop_play();

	/*	Return Value : None
    */
	public static native void ts_player_stop_play_and_tunning();
	
	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL	
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play(int serviceType, int serviceIndex, int timeDelayMs, boolean lockPass);
	
	/*	Return Value : RET_OK
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play_current(boolean lockPass, boolean smallVideoWindow);
	
	/*	Return Value : RET_OK
					   RET_NO_CHANNEL
	*/	
	public static native int ts_player_play_switch_audio(int audioIndex);
	
	/*	Return Value : RET_OK
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play_up(int timeDelayMs, boolean lockPass);
	
	/*	Return Value : RET_OK
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play_down(int timeDelayMs, boolean lockPass);
	
	/*	Return Value : RET_OK
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play_previous(boolean lockPass);
	
	/*	Return Value : RET_OK
					   RET_NO_VIDEO_CHANNEL
					   RET_NO_AUDIO_CHANNEL
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play_switch_tv_radio(boolean lockPass);
	
	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL
					   RET_NO_CHANNEL
					   RET_SERVICE_LOCK
	*/	
	public static native int ts_player_play_by_channel_number(int chnum, boolean lockPass);


// tuner
	
	/*	Return Value : None
    */
	public static native void tuner_require_tuner_status(int tunerIndex);
	
	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/	
	public static native int tuner_get_tuner_status(int tunerIndex, mw_data.tuner_signal_status o_tuner_status_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
	*/	
	public static native int tuner_dvb_s_lock_tp(int tunerIndex, int satIndex, int tpIndex, boolean bWaitActualLock);
	
	/*	Return Value : RET_OK
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_TP_INDEX
	*/	
	public static native int tuner_dvb_t_lock_tp(int tunerIndex, int areaIndex, int tpIndex, boolean bWaitActualLock);
	
	/*	Return Value : None
    */
	public static native void tuner_dvb_t_set_antenna_power(int tunerIndex, int antenna_power);
	
	/*	Return Value : None
    */
	public static native void tuner_unlock_tp(int tunerIndex, int system_type, boolean bWaitActualStopLock);

// search	

	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
					   RET_INVALID_SAT_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_SAT_INDEX_LIST
					   RET_INVALID_TP_INDEX_LIST
					   RET_MEMORY_ERROR
	*/	
	public static native int search_dvb_s_start(int searchType, int[] satIndexList, int[] tpIndexList, int maxChannelCountShow);
	
	/*	Return Value : None
    */
	public static native void search_dvb_s_stop();
	
	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
					   RET_INVALID_AREA_INDEX
					   RET_INVALID_TP_INDEX
					   RET_INVALID_AREA_INDEX_LIST
					   RET_INVALID_TP_INDEX_LIST
					   RET_MEMORY_ERROR
	*/	
	public static native int search_dvb_t_start(int searchType, int[] areaIndexList, int[] tpIndexList, int maxChannelCountShow);
	
	/*	Return Value : None
    */
	public static native void search_dvb_t_stop();

// teletext

	/*	Return Value : RET_OK
					   RET_GET_OBJECT_FAILED
					   RET_GET_BUFFER_FAILED
					   RET_SYSTEM_ERROR
					   RET_INVALID_OBJECT
	*/	
	public static native int teletext_start(int pid, int magazine_number, int page_number, Object i_bitmap_clazz);
	
	/*	Return Value : None
    */
	public static native void teletext_stop();
	
	/*	Return Value : None
    */
	public static native void teletext_lock();
	
	/*	Return Value : None
    */
	public static native void teletext_unlock();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_next_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_prev_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_next_sub_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_prev_sub_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_page(int page);
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_color_link(int color);
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_home();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_first_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_last_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_goto_offset_page(int offset);
	
	/*	Return Value : current teletext page number
	*/
	public static native int teletext_get_current_page();
	
	/*	Return Value : RET_OK
					   RET_SYSTEM_ERROR
	*/
	public static native int teletext_get_sub_page_info(mw_data.ttx_sub_page_info o_ttx_sub_page_clazz);

// subtitle

	/*	Return Value : RET_OK
					   RET_GET_OBJECT_FAILED
					   RET_GET_BUFFER_FAILED
					   RET_SYSTEM_ERROR
					   RET_INVALID_OBJECT
	*/	
	public static native int subtitle_start(int pid, boolean teletext_subtitle, int composition_page_id, int ancillary_page_id, Object i_bitmap_clazz);

	/*	Return Value : RET_OK
					   RET_GET_OBJECT_FAILED
					   RET_GET_BUFFER_FAILED
					   RET_SYSTEM_ERROR
					   RET_INVALID_OBJECT
	*/	
	public static native int subtitle_start_cc(Object i_bitmap_clazz);
	
	/*	Return Value : None
    */
	public static native void subtitle_stop();
	
	/*	Return Value : picture width of current subtitle
    */
	public static native int subtitle_get_picture_width();
	
	/*	Return Value : picture height of current subtitle
    */
	public static native int subtitle_get_picture_height();
	
	/*	Return Value : None
    */
	public static native void subtitle_lock();
	
	/*	Return Value : None
    */
	public static native void subtitle_unlock();


// epg
	/*	Return Value : None
    */
	public static native void epg_require_pf_event();
	
	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/	
	public static native int epg_get_pf_event(mw_data.service i_service_clazz, mw_data.epg_pf_event o_epg_current_event_clazz, mw_data.epg_pf_event o_epg_next_event_clazz);

	/*	Return Value : None
    */
	public static native void epg_set_schedule_event_day_offset(int dayOffset);

	/*	Return Value : event count of given tsId && serviceId && weekDay
	*/	
	public static native int epg_lock_get_schedule_event_count(int dayOffset, mw_data.service i_service_clazz, mw_data.date i_current_date_clazz);

	/*	Return Value : event id
	*/	
	public static native int epg_lock_get_schedule_event_id_by_index(int dayOffset, int index, mw_data.service i_service_clazz, mw_data.date i_current_date_clazz);

	/*	Return Value : None
    */
	public static native void epg_unlock_schedule_event();
	
	/*	Return Value : RET_OK
					   RET_EVENT_NOT_FOUND
					   RET_INVALID_OBJECT
	*/	
	public static native int epg_get_schedule_event_by_event_id(int dayOffset, int event_id, mw_data.service i_service_clazz, mw_data.date i_current_date_clazz, mw_data.epg_schedule_event o_epg_event_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
					   RET_EVENT_NOT_FOUND
					   RET_FUNCTION_NOT_SUPPORTTED
	*/	
	public static native int epg_get_schedule_extended_event_by_event_id(int dayOffset, int index, mw_data.service i_service_clazz, mw_data.date i_current_date_clazz, mw_data.epg_extended_event o_epg_extended_event_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/	
	public static native int epg_get_atsc_channel_ett(mw_data.service service_clazz);
	
// pvr

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/	
	public static native int pvr_record_init_record_list(String storage_mount_dir);

	/*	Return Value : None
	*/	
	public static native void pvr_record_exit_record_list();

	/*	Return Value : record count of the inited record list
	*/	
	public static native int pvr_record_get_record_count();

	/*	Return Value : RET_OK
					   RET_ALLOC_OBJECT_FAILED
					   RET_NO_THIS_RECORD
	*/	
	public static native int pvr_record_get_record_info(int recordIndex, mw_data.pvr_record o_pvr_record_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_RECORD_INDEX
					   RET_MEMORY_ERROR
					   RET_INVALID_RECORD_INDEX_LIST
	*/	
	public static native int pvr_record_delete_record(int[] recordIndexList);
	
	/*	Return Value : RET_OK
					   RET_INVALID_RECORD_INDEX
					   RET_INVALID_OBJECT
	*/	
	public static native int pvr_record_change_record_name(int recordIndex, String recordName);
	
	/*	Return Value : RET_OK
					   RET_INVALID_RECORD_INDEX
	*/	
	public static native int pvr_record_set_record_lock(int recordIndex, boolean is_locked);

	/*	Return Value : RET_OK
					   RET_ALREADY_RECORDING
					   RET_NO_THIS_CHANNEL
					   RET_INVALID_OBJECT
	*/	
	public static native int pvr_record_start(int serviceType, int serviceIndex, int duration, String storage_mount_dir, String event_name);

	/*	Return Value : None
	*/	
	public static native void pvr_record_stop();

	/*	Return Value : RET_OK
					   RET_PLAYBACK_FAILED
	*/	
	public static native int pvr_playback_start(int recordIndex);

	/*	Return Value : None
	*/	
	public static native void pvr_playback_stop();
	
	/*	Return Value : None
	*/	
	public static native void pvr_playback_pause();
	
	/*	Return Value : None
	*/	
	public static native void pvr_playback_resume();
	
	/*	Return Value : None
	*/	
	public static native void pvr_playback_fast_forward(int speed);
	
	/*	Return Value : None
	*/	
	public static native void pvr_playback_fast_backward(int speed);
	
	/*	Return Value : None
	*/	
	public static native void pvr_playback_seek(int posSeconds, boolean start);
	
	/*	Return Value : None
	*/	
	public static native void pvr_playback_switch_audio(int audioPid, int audioFmt);
	
	
	/*	Return Value : RET_OK
					   RET_ALREADY_RECORDING
					   RET_NO_THIS_CHANNEL
					   RET_INVALID_OBJECT
	*/	
	public static native int pvr_timeshift_start(int serviceType, int serviceIndex, int duration, String storage_mount_dir);

	/*	Return Value : None
	*/	
	public static native void pvr_timeshift_stop();

	/*	Return Value : None
	*/	
	public static native void pvr_timeshift_pause();
	
	/*	Return Value : None
	*/	
	public static native void pvr_timeshift_resume();
	
	/*	Return Value : None
	*/	
	public static native void pvr_timeshift_fast_forward(int speed);
	
	/*	Return Value : None
	*/	
	public static native void pvr_timeshift_fast_backward(int speed);
	
	/*	Return Value : None
	*/	
	public static native void pvr_timeshift_seek(int posSeconds, boolean start);


// book

	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
	*/	
	public static native int book_set_event_conflict_check_mode(int mode);

	/*	Return Value : event count of book list
	*/	
	public static native int book_get_event_count();
	
	/*	Return Value : RET_OK
					   RET_EVENT_NOT_FOUND
					   RET_INVALID_SERVICE
					   RET_INVALID_OBJECT
	*/	
	public static native int book_get_event_by_index(int eventIndex, mw_data.book_event_data o_book_event_clazz);

	/*	Return Value : None
	*/	
	public static native void book_lock_event_check();

	/*	Return Value : None
	*/	
	public static native void book_unlock_event_check();
	
	/*	Return Value : RET_OK
					   RET_INVALID_SERVICE
					   RET_INVALID_BOOK_TYPE
					   RET_EVENT_NOT_FOUND
					   RET_INVALID_OBJECT
					   RET_BOOK_EVENT_INVALID
					   RET_BOOK_CONFLICT_EVENT
					   RET_MEMORY_ERROR
	*/	
	public static native int book_epg_add_event(int dayOffset, int event_id, int book_type, mw_data.service i_service_clazz, mw_data.date i_current_date_clazz, mw_data.book_event_data o_conflict_book_event_clazz);
	
	/*	Return Value : RET_OK
					   RET_EVENT_NOT_FOUND
	*/	
	public static native int book_delete_event_by_index(int eventIndex);
	
	/*	Return Value : RET_OK
					   RET_EVENT_NOT_FOUND
	*/	
	public static native int book_handle_event_by_index(int eventIndex);
	
	/*	Return Value : RET_OK
					   RET_EVENT_NOT_FOUND
					   RET_INVALID_OBJECT
	*/	
	public static native int book_epg_delete_event(int dayOffset, int event_id, mw_data.service i_service_clazz, mw_data.date i_current_date_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_SERVICE
					   RET_INVALID_OBJECT
					   RET_INVALID_BOOK_TYPE
					   RET_INVALID_BOOK_MODE
					   RET_BOOK_EVENT_INVALID
					   RET_BOOK_CONFLICT_EVENT
					   RET_MEMORY_ERROR
	*/	
	public static native int book_manual_add_event(int book_type, int book_mode, mw_data.service i_service_clazz, mw_data.date_time i_start_date_time_clazz, int durationMinutes, mw_data.book_event_data o_conflict_book_event_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_SERVICE
					   RET_EVENT_NOT_FOUND
					   RET_INVALID_OBJECT
					   RET_INVALID_BOOK_TYPE
					   RET_INVALID_BOOK_MODE
					   RET_BOOK_EVENT_INVALID
					   RET_BOOK_CONFLICT_EVENT
					   RET_MEMORY_ERROR
	*/	
	public static native int book_manual_modify_event(int event_index, int book_type, int book_mode, mw_data.service i_service_clazz, mw_data.date_time i_start_date_time_clazz, int durationMinutes, mw_data.book_event_data o_conflict_book_event_clazz);

// channel edit
	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
    public static native int channel_edit_init();

	/*	Return Value : None
	*/
    public static native void channel_edit_exit();

	/*	Return Value : true , true means at least one servive is selected
	                   false, false means not any service is selected
    */
    public static native boolean channel_edit_has_selection();
	
	/*	Return Value : true , true means the appointted service is selected
	                   false, false means the appointted service is not selected
    */
    public static native boolean channel_edit_check_selection(int serviceIndex);

	/*	Return Value : true , true means the appointted service is deleted
	                   false, false means the appointted service is not deleted
    */
    public static native boolean channel_edit_check_delete(int serviceIndex);

	/*	Return Value : true , true means the appointted service is skipped
	                   false, false means the appointted service is not skipped
    */
    public static native boolean channel_edit_check_skip(int serviceIndex);

	/*	Return Value : true , true means the appointted service is locked
	                   false, false means the appointted service is not locked
    */
    public static native boolean channel_edit_check_lock(int serviceIndex);

	/*	Return Value : true , true means the appointted service is favorited
	                   false, false means the appointted service is not favorited
    */
    public static native int channel_edit_get_favorite(int serviceIndex);

	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_select_single(boolean bSelect, int serviceIndex);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_select_all(boolean bSelect);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_select_region(boolean bSelect, int regionIndex);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_select_scrambled(boolean bSelect);

	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_delete_single(boolean bDelete, int serviceIndex);

	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_skip_single(boolean bSkip, int serviceIndex);

	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_lock_single(boolean bLock, int serviceIndex);

	/*	Return Value : RET_OK
					   RET_NO_THIS_CHANNEL
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_favorite_single(int favorite, int serviceIndex);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_delete_selection(boolean bDelete);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_skip_selection(boolean bSkip);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_lock_selection(boolean bLock);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_set_favorite_selection(int favorite);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_sort(int sortType);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_move_selection(int destServiceIndex);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
					   RET_INVALID_OBJECT
					   RET_NO_THIS_CHANNEL
	*/
	public static native int channel_edit_set_service_name(int serviceIndex, String serviceName);
	
	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
					   RET_INVALID_OBJECT
					   RET_NO_THIS_CHANNEL
	*/
	public static native int channel_edit_set_service_pids(int serviceIndex, int videoPid, int videoStreamType, int pcrPid, mw_data.aud_info[] audioInfoList);
	
	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
					   RET_INVALID_OBJECT

		Input Params : 
		               for system_type != SYSTEM_TYPE_TER, set front_end_type , ter_profile, dvb_t2_plp_id to 0
		               for system_type == SYSTEM_TYPE_TER, front_end_type to FRONT_END_TYPE_*, ter_profile to DVB_T_PROFILE_* or DVB_T2_PROFILE_* 
	*/
	public static native int channel_edit_add_service(int system_type, int regionIndex, int tpIndex, int front_end_type, int ter_profile, int dvb_t2_plp_id, String serviceName, int videoPid, int videoStreamType, int pcrPid, mw_data.aud_info[] audioInfoList);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
					   RET_NO_THIS_CHANNEL
	*/
	public static native int channel_edit_delete_single(int serviceIndex);

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_delete_selection();

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_save_operation();

	/*	Return Value : RET_OK
					   RET_MEMORY_ERROR
	*/
	public static native int channel_edit_cancel_operation();
	
	/*	Return Value : true means data has been modified, need save
					   false means data has not been modified, not need to save
	*/
    public static native boolean channel_edit_check_data_modified();


// motor
	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_move_east();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_move_east_continues();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_move_west();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_move_west_continues();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_halt();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_disable_limit();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_set_east_limit();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_set_west_limit();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_store_position(int position);

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_goto_position(int position);

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_goto_reference();

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc12_recalculate(int position);

	/*	Return Value : true means operation succeed
					   false means operation failed
	*/
    public static native boolean diseqc13_goto_position(int satIndex);

	/*	Return Value : true means enable auto moving
					   false means disable auto moving
	*/
    public static native void diseqc_motor_enable_auto_moving(boolean bEnable);


// global
	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
					   RET_SYSTEM_ERROR
	*/
	public static native int global_set_switch_channel_mode(int mode);

	/*	Return Value : RET_OK
					   RET_INVALID_DATA_LENGTH
					   RET_INVALID_OBJECT
	*/
	public static native int global_set_system_ISO_639_Language_Code(String i_iso639LangCode);

	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
	*/
    public static native int global_set_local_location(int location, int longitude, int hemisphere, int latitude);
	
	/*	Return Value : None
	*/
    public static native void global_set_time_zone(int gmtOffsetMinutes);

	/*	Return Value : RET_OK
					   RET_INVALID_DATA_LENGTH
	*/
	public static native int global_set_default_audio_ISO_639_Language_Code(String i_firstAudioIso639LangCode, String i_secondAudioIso639LangCode);
	
	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
	*/
    public static native int global_set_search_service_type(int type);

	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
	*/
    public static native int global_set_search_tv_type(int type);

	/*	Return Value : None
	*/
    public static native void global_set_search_nit(boolean nitOn);

	/*	Return Value : None
	*/
    public static native void global_set_lcn_option(boolean lcnOn);

	/*	Return Value : None
	*/
    public static native void global_set_second_tuner_loop_lock(boolean loopLock, int loopPeriodMs);

	/*	Return Value : None
		value default : true
	*/
    public static native void global_set_timeshift_start_with_blank(boolean blank);

	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
		value default : mode = MW.DYNAMIC_SERVICE_UPDATE_MODE_SERVICE   	detectPeriodMs = 15000		   
	*/
    public static native int global_set_dynamic_service_update_mode(int mode, int detectPeriodMs);

	/*	Return Value : None
		value default : needCompareTsId : true     needReceiveOtherEit : false
	*/
    public static native void global_set_epg_receive_mode(boolean needCompareTsId, boolean needReceiveOtherEit);

	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
		value default : MW.PVR_RECORD_MODE_CURRENT_SERVICE_WITH_SI_PSI
	*/
	public static native int global_set_pvr_record_mode(int mode);

	/*	Return Value : None
		value default : MW.ATSC_SYSTEM_TYPE_AIR
	*/
    public static native void global_atsc_set_system_type(int systemType);
    
// mw
	
	/*	Return Value : system tuner config of current main board
	*/
	public static native int get_system_tuner_config();
	
	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/
	public static native int get_system_tuner_info(mw_data.system_tuner_info o_system_tuner_info_clazz);	
	
	/*	Return Value : main ter tuner type of current main board
	*/
	public static native int get_main_ter_tuner_type();
	
	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/
	public static native int get_date_time(mw_data.date_time o_date_time_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/
	public static native int set_date_time(mw_data.date_time i_date_time_clazz);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
	*/
	public static native int get_date(int dayOffset, mw_data.date i_date_clazz, mw_data.date o_date_clazz);
	
	/*	Return Value : None
	*/
	public static native void enter_standby();
	
	/*	Return Value : RET_OK
					   RET_GET_OBJECT_FAILED
					   RET_GET_JAVA_METHOD_FAILED
					   RET_ALLOC_OBJECT_FAILED
	*/
    public static native int register_event_cb(Object i_clazz);
	
	/*	Return Value : RET_OK
					   RET_INVALID_TYPE
	*/
    public static native int register_event_type(int event_type, boolean enable);

// special

	/*	Return Value : None
		value default : false
	*/
    public static native void special_dvb_set_default_h264_as_hd_channel(boolean bSet);

	/*	Return Value : None
		value default : all set to 8000
	*/
    public static native void special_dvb_set_table_search_timeout(int patTimeout, int pmtTimeout, int nitTimeout, int sdtTimeout, int batTimeout);

	/*	Return Value : None
		value default : all set to 8000
	*/
    public static native void special_atsc_set_table_search_timeout(int mgtTimeout, int vctTimeout);

	/*	Return Value : RET_OK
					   RET_INVALID_OBJECT
		value default : "ISO6937"
	*/
    public static native int special_dvb_set_text_default_char_coding(String sCoding);

	/*	Return Value : None
		value default : true
	*/
	public static native void specail_set_show_no_audio_play_status(boolean show);

	/*	Return Value : None
		value default : false
	*/
	public static native void specail_set_remove_duplicate_service_while_search(boolean remove);

	/*	Return Value : None
		value default : false
	*/
	public static native void specail_dvb_set_clear_epg_event_start_with_special_chars(boolean clear);

	/*	Return Value : None
		value default : false
	*/
	public static native void specail_set_keep_service_user_data_which_search(boolean keep);
}

//////////////////////////////////////////////////////////////////////////
/////
// TH DTV MIDDLE WARE DEFINES, PLEASE DO NOT MAKE ANY CHANGE OF THIS FILE
/////
//////////////////////////////////////////////////////////////////////////

