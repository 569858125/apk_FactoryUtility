//////////////////////////////////////////////////////////////////////////
/////
// TH DTV MIDDLE WARE DEFINES, PLEASE DO NOT MAKE ANY CHANGE OF THIS FILE
/////
//////////////////////////////////////////////////////////////////////////

package th.dtv;

public class mw_data {
	public static class system_tuner_info {
		public	int sat_tuner_id_1;
		public	int sat_tuner_id_2;
		public	int ter_tuner_id_1;
		public	int ter_tuner_id_2;
		
		public	int sat_tuner_type_1;
		public	int sat_tuner_type_2;
		public	int ter_tuner_type_1;
		public	int ter_tuner_type_2;
	}
	
	public static class region_info {
		public 	int system_type;
		public 	int region_index;		

		public	String region_name;
	} 

	public static class dvb_s_sat {
		public 	int sat_index;  /* read only item */
		public 	int sat_pos;  /* read only item , pos of selected satellites, -1 if the sat is not selected*/

		public	boolean selected;
			
		public	String sat_name;
		public	int lnb_low;
		public	int lnb_high;
		public	int sat_degree_dec;
		public	int sat_degree_point;
		public	int sat_position;
		public	int k22;
		public	int lnb_type;
		public	int lnb_power;
		public	int diseqc_type;
		public	int diseqc_port;
		public	int motor_type;
		public	int tp_count;  /* read only item */

		public	int diseqc12_position; 

	//SAT_SCR		
		public	int unicable_type;
		public	int scr_number;
		public	int scr_bandPassFrequency;
		//SAT_SCR_POSITION		
		public	int scr_position;
		//
	//		
	} 

	public static class dvb_s_tp {
		public	int sat_index;  /* read only item */
		public 	int sat_pos;  /* read only item , pos of selected satellites, -1 if the sat is not selected*/
		public	int tp_index;  /* read only item */		
		
		public	int frq;
		public	int sym;
		public  int pol;
	}

	public static class dvb_t_area {
		public 	int area_index;  /* read only item */

		public	String area_name; //reserved for future use

		public	int tp_count;  /* read only item */
	} 

	public static class dvb_t_tp {
		public	int area_index;  /* read only item */
		public	int tp_index;  /* read only item */		
		
		public	int frq;
		public	int bandwidth;
		public	String channel_number;  /* read only item */	
	}

	public static class aud_info {
		public	String ISO_639_language_code;
		public	int audio_pid;
		public	int audio_stream_type;
	}		
	
	public static class ttx_info {
		public	String ISO_639_language_code;
		public	int teletext_type;
		public	int teletext_magazine_number;
		public	int teletext_page_number;
		public	int pid;
	}		
	
	public static class sub_info {
		public	String ISO_639_language_code;
		public	int subtitling_type;
		public	int composition_page_id;
		public	int ancillary_page_id;
		public  int pid;
	}		
	
	public static class service {
		public int service_index;
		
		public 	String service_name;
		public 	int ts_id;
		public	int service_id;
		public	int on_id; //used for 'source_id' when ATSC mode
		public	int region_index;//for DVB S, it's sat index, for DVB T, ISDB T, ATSC, it's area index
		public	int tp_index;
		public	int video_pid;
		public	boolean is_scrambled;
		public	boolean is_locked;
		public	boolean is_skipped;
		public	int favorite;
		public	int video_stream_type;
		public	int audio_index;
		public	aud_info[] audio_info;
		public	ttx_info[] teletext_info;
		public	sub_info[] subtitle_info;
		public	int pcr_pid;
		public 	int service_type;
		public	boolean is_hd;
		public	int system_type;
		public 	int channel_number_display;
		public	int front_end_type;
		public	int ter_profile; //valid only when system_type == SYSTEM_TYPE_TER
		public	int dvb_t2_plp_id; //valid only when system_type == SYSTEM_TYPE_TER and front_end_type == FRONT_END_TYPE_DVBT_2
		
		public 	String extended_service_name; //valid only when MW.get_main_ter_tuner_type() == MW.TUNER_TYPE_ATSC
                                              //call func MW.epg_get_atsc_channel_ett will override this field value
		
		public  int major_channel_number; //valid only when MW.get_main_ter_tuner_type() == MW.TUNER_TYPE_ATSC
		public  int minor_channel_number; //valid only when MW.get_main_ter_tuner_type() == MW.TUNER_TYPE_ATSC
	} 
	
	public static class tuner_signal_status {
		public	boolean error;
		public	boolean locked;
		public	int strength;
		public	int quality;
		public	int front_end_type;
		public	long ber;
	}
		
	public static class date_time {
		public int year;
		public int month;
		public int day;
		public int hour;
		public int minute;
		public int second;
	}
		
	public static class date {
		public int year;
		public int month;
		public int day;
		public int weekNo;
	}

	public static class pvr_record {
		public String service_name;
		public String event_name;

		public long file_size;
		public int duration;
		public int current_play_position;
		
		public int record_year;
		public int record_month;
		public int record_day;
		public int record_hour;
		public int record_minute;
		public int record_second;

		public	int video_pid;
		public	int video_stream_type;
		public	int audio_index;
		public	aud_info[] audio_info;
		public	ttx_info[] teletext_info;
		public	sub_info[] subtitle_info;
		public int service_type;
		public	boolean is_hd;
		public	boolean is_locked;
	}	

// epg		
	public static class epg_pf_event {
		public String event_name;

		public int play_progress;
		public int parental_rating;

		public int start_year;
		public int start_month;
		public int start_day;
		public int start_hour;
		public int start_minute;

		public int end_year;
		public int end_month;
		public int end_day;
		public int end_hour;
		public int end_minute;
	}

	public static class epg_schedule_event {
		public String event_name;
		public String event_text;
		
		public int event_id;
		public int playing_status;
		public int parental_rating;

		public int start_year;
		public int start_month;
		public int start_day;
		public int start_hour;
		public int start_minute;

		public int end_year;
		public int end_month;
		public int end_day;
		public int end_hour;
		public int end_minute;
		
		public int book_status;

		public boolean has_extended_event_info;
	}

	public static class epg_extended_event {
		public String event_text;

		public String[] items_descriptor;
		public String[] items_text;
	}	

//book
	public static class book_callback_event_data {
		public int book_type;
		public int book_mode;
		
		public int event_index;

		public int service_type;
		public int service_index;
		public boolean is_all_region_service_index;
		
		public int duration_seconds;
		
		public String service_name;
		public String event_name;
		
		public int left_time_seconds;
	}

	public static class book_event_data {
		public int start_year;
		public int start_month;
		public int start_day;
		public int start_hour;
		public int start_minute;

		public int end_year;
		public int end_month;
		public int end_day;
		public int end_hour;
		public int end_minute;

		public int book_type;
		public int book_mode;

		public int event_index;

		public int service_type;
		public int service_index;
		public boolean is_all_region_service_index;
		
		public String service_name;
		public String event_name;
	}

// search status	
	public static class search_status_update_channel_list {
		public	String[] tv_channels_name;
		public	String[] radio_channels_name;

		public	int[] tv_channels_number;
		public	int[] radio_channels_number;
	}		

	public static class search_status_dvbs_blind_scan_progress {
		public	boolean locked;

		public	int progress;
		public	int progress_total;
	}	

	public static class search_status_dvbs_blind_scan_params {
		public	boolean locked;

		public	int search_sat_count;
		public	int search_sat_pos; /* from 0 to search_sat_count */
		public	int search_sat_index; /* current sat index, used to get sat info */
		
		public	String sat_name;
		public	int sat_degree_dec;
		public	int sat_degree_point;
		public	int sat_position;

		public	int frq;
		public	int sym;
		public	int pol;	
	}	
	
	public static class search_status_dvbs_params {
		public	int search_sat_count;
		public	int search_sat_pos; /* from 0 to search_sat_count */
		public	int search_sat_index; /* current sat index, used to get sat info */
		public	int search_tp_count;
		public	int search_tp_pos; /* from 0 to search_tp_count */
		
		public	String sat_name;
		public	int sat_degree_dec;
		public	int sat_degree_point;
		public	int sat_position;

		public	int frq;
		public	int sym;
		public	int pol;		
		
		public	int progress;
		public	int progress_total;
	}		

	public static class search_status_dvbt_params {
		public	int search_area_count;
		public	int search_area_pos; /* from 0 to search_area_count */
		public	int search_area_index;
		public	int search_tp_count;
		public	int search_tp_pos; /* from 0 to search_tp_count */
		
		public	String area_name; //reserved for future use

		public	String channel_number;
		public	int frq;
		public	int bandwidth;		
		public	int front_end_type;
		public	int ter_profile; //valid only when system_type == SYSTEM_TYPE_TER
		public	int dvb_t2_plp_id; //valid only when system_type == SYSTEM_TYPE_TER and front_end_type == FRONT_END_TYPE_DVBT_2
		
		public	int progress;
		public	int progress_total;
	}	
	
	public static class search_status_result {
		public	int tv_service_count;
		public	int radio_service_count;	
	}		

//teletext current sub page numbers
	public static class ttx_sub_page_info {
		public	int[] sub_page_nos;
	}		
} 

//////////////////////////////////////////////////////////////////////////
/////
// TH DTV MIDDLE WARE DEFINES, PLEASE DO NOT MAKE ANY CHANGE OF THIS FILE
/////
//////////////////////////////////////////////////////////////////////////

