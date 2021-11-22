package com.vasantlab.tag.util;

import com.thingmagic.Gen2;

public enum TagLockOperation {
	EPC{
		@Override
		public Gen2.LockAction execute(String lock){
			Gen2.LockAction lockAction = null;
			if(lock.equalsIgnoreCase(TagLockConstant.LOCK) || lock.equalsIgnoreCase(TagLockConstant.LOCKED)) {
				lockAction = Gen2.LockAction.EPC_LOCK;
			}
			if (lock.equalsIgnoreCase(TagLockConstant.PMLOCK)){
				lockAction = Gen2.LockAction.EPC_PERMALOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.UNLOCK) || lock.equalsIgnoreCase(TagLockConstant.UNLOCKED)){
				lockAction = Gen2.LockAction.EPC_UNLOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.PMUNLOCK) || lock.equalsIgnoreCase(TagLockConstant.PMUNLOCKED)){
				lockAction = Gen2.LockAction.EPC_PERMAUNLOCK;
			}
			return lockAction;
		}
	},ACCESS{
		@Override
		public Gen2.LockAction execute(String lock){
			Gen2.LockAction lockAction = null;
			if(lock.equalsIgnoreCase(TagLockConstant.LOCK) || lock.equalsIgnoreCase(TagLockConstant.LOCKED)){
				lockAction =  Gen2.LockAction.ACCESS_LOCK;
			}
			if (lock.equalsIgnoreCase(TagLockConstant.PMLOCK)){
				lockAction =  Gen2.LockAction.ACCESS_PERMALOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.UNLOCK) || lock.equalsIgnoreCase(TagLockConstant.UNLOCKED)){
				lockAction =  Gen2.LockAction.ACCESS_UNLOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.PMUNLOCK) || lock.equalsIgnoreCase(TagLockConstant.PMUNLOCKED)){
				lockAction =  Gen2.LockAction.ACCESS_PERMAUNLOCK;
			}
			return lockAction;
		}
	},KILL{
		@Override
		public  Gen2.LockAction execute(String lock){
			Gen2.LockAction lockAction = null;
			if(lock.equalsIgnoreCase(TagLockConstant.LOCK) || lock.equalsIgnoreCase(TagLockConstant.LOCKED)){
				lockAction =  Gen2.LockAction.KILL_LOCK;
			}
			if (lock.equalsIgnoreCase(TagLockConstant.PMLOCK)){
				lockAction =  Gen2.LockAction.KILL_PERMALOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.UNLOCK)|| lock.equalsIgnoreCase(TagLockConstant.UNLOCKED)){
				lockAction =  Gen2.LockAction.KILL_UNLOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.PMUNLOCK) ||lock.equalsIgnoreCase(TagLockConstant.PMUNLOCKED)){
				lockAction =  Gen2.LockAction.KILL_PERMAUNLOCK;
			}
			return lockAction;
		}
	
	},USERMEMORY{
		@Override
		public Gen2.LockAction execute(String lock){
		Gen2.LockAction lockAction = null;
			if(lock.equalsIgnoreCase(TagLockConstant.LOCK) || lock.equalsIgnoreCase(TagLockConstant.LOCKED)){
				lockAction =  Gen2.LockAction.USER_LOCK;
			}
			if (lock.equalsIgnoreCase(TagLockConstant.PMLOCK)){
				lockAction =  Gen2.LockAction.USER_PERMALOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.UNLOCK) || lock.equalsIgnoreCase(TagLockConstant.UNLOCKED)){
				lockAction =  Gen2.LockAction.USER_UNLOCK;
			}
			if(lock.equalsIgnoreCase(TagLockConstant.PMUNLOCK) || lock.equalsIgnoreCase(TagLockConstant.PMUNLOCKED)){
				lockAction =  Gen2.LockAction.USER_PERMAUNLOCK;
			}
			return lockAction;
		}
	
	};

	public abstract Gen2.LockAction execute(String lock);
}



