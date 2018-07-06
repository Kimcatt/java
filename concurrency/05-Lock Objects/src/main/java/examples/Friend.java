package examples;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Friend {
	private final String name;
	private final Lock lock = new ReentrantLock();

	public Friend(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean impendingBow(Friend bower) {
		Boolean selfLocked = false;
		Boolean otherLocked = false;
		try {
			selfLocked = lock.tryLock();
			otherLocked = bower.lock.tryLock();
		} finally {
			if (!(selfLocked && otherLocked)) {
				if (selfLocked) {
					lock.unlock();
				}
				if (otherLocked) {
					bower.lock.unlock();
				}
			}
		}
		return selfLocked && otherLocked;
	}

	public void bow(Friend bower) {
		if (impendingBow(bower)) {
			try {
				System.out.format("%s: %s has" + " bowed to me!%n", this.name, bower.getName());
				bower.bowBack(this);
			} finally {
				lock.unlock();
				bower.lock.unlock();
			}
		} else {
			System.out.format(
					"%s: %s started" + " to bow to me, but saw that" + " I was already bowing to" + " him.%n",
					this.name, bower.getName());
		}
	}

	public void bowBack(Friend bower) {
		System.out.format("%s: %s has" + " bowed back to me!%n", this.name, bower.getName());
	}
}
