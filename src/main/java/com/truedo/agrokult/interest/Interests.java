package com.truedo.agrokult.interest;

import java.util.List;

public class Interests {
		int user_id;
		List<String> interest ;
		
//create table interests(user_id int not null, interest varchar(100) not null)
		
		
		public int getUser_id() {
			return user_id;
		}
		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}
		public List<String> getInterest() {
			return interest;
		}
		public void setInterest(List<String> interest) {
			this.interest = interest;
		}
		
		
		
}
